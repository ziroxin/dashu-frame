<template>
	<!-- 订单详情 -->
	<view class="container u-skeleton">
		<!-- #ifndef MP-ALIPAY -->
		<u-navbar back-icon-color="white" class="navbar" title-color="white" title="订单详情" :background="backGround"
			:isFixed="false">
		</u-navbar>
		<!-- #endif -->
		<!-- #ifdef MP-ALIPAY -->
		<u-navbar class="navbar" title-color="white" :background="backGround" :is-back="false" :isFixed="false">
		</u-navbar>
		<!-- #endif -->

		<view class="order-status">
			<view class="status-text u-skeleton-fillet">
				{{orderInfo.status_text}}
			</view>
			<view class="payment-date u-skeleton-fillet" v-if="orderInfo.status==0">
				<u-count-down :timestamp="countDown" separator-color="white" bg-color="transparent" color="white"
					@end="timestampEnd">
				</u-count-down> 后订单自动关闭
			</view>
		</view>

		<view class="food-cart">
			<view class="shop-name u-skeleton-fillet">
				{{orderInfo.name}}
			</view>
			<view class="foods">
				<view class="item u-skeleton-fillet" v-for="(item,index) in orderInfo.goods_sku" :key="index">
					<view class="left">
						<view class="img-box">
							<u-image class="foods-image-box u-skeleton-rect" width="100" height="100" :src="item.img"
								border-radius="8rpx">
								<view class="img-status-text" slot="loading">
									加载中
								</view>
								<view class="img-status-text" slot="error">
									加载失败
								</view>
							</u-image>
						</view>
						<view class="food-info">
							<view class="food-name">
								{{item.goods_name}}
							</view>
							<view class="food-number">
								*{{item.quantity}}
							</view>
						</view>
					</view>
					<view class="price">
						<text class="unit">¥</text><text class="price">{{item.price}}</text>
					</view>
				</view>
				<!-- <view class="item u-skeleton-fillet">
					<view class="left">
						打包费
					</view>
					<view class="price">
						<text class="unit">¥</text><text class="price">1</text>
					</view>
				</view>
				<view class="item u-skeleton-fillet">
					<view class="left">
						外送费
					</view>
					<view class="price">
						<text class="unit">¥</text><text class="price">1</text>
					</view>
				</view>
				<view class="item u-skeleton-fillet">
					<view class="left">
						优惠券
					</view>
					<view class="price">
						-<text class="unit">¥</text><text class="price">1</text>
					</view>
				</view> -->
			</view>
			<view class="reality-price u-skeleton-fillet">
				<view class="label">
					合计
				</view>
				<view class="reality">
					<text class="unit">¥</text><text class="price">
						{{orderInfo.price}}
					</text>
				</view>
			</view>
		</view>

		<view class="order-info">
			<view class="title u-skeleton-fillet">
				订单信息
			</view>
			<view class="item u-skeleton-fillet">
				<view class="label">
					订单号
				</view>
				<view class="value">
					{{orderInfo.id}} <text class="copy-btn" @click="$copy(orderInfo.id)">复制</text>
				</view>
			</view>
			<view class="item u-skeleton-fillet">
				<view class="label">
					订单类型
				</view>
				<view class="value">
					{{orderInfo.type==1?'自取单':'桌号单'}}
				</view>
			</view>
			<view class="item u-skeleton-fillet" v-if="orderInfo.type=='1'">
				<view class="label">
					取餐号
				</view>
				<view class="value">
					{{orderInfo.card}}
				</view>
			</view>
			<view class="item u-skeleton-fillet">
				<view class="label">
					下单时间
				</view>
				<view class="value">
					{{orderInfo.create_time}}
				</view>
			</view>
		</view>

		<view class="handle-box">
			<view class="other-use btn" @click="cancel">
				{{cancelText}}
			</view>
			<view class="confirm btn" @click="payNow(orderInfo)">
				{{confirmText}}
			</view>
		</view>
		<u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
		<u-modal v-model="cancelShow" title="取消订单" content="是否取消订单？" confirm-text="确认" cancel-text="取消"
			show-cancel-button @confirm="confirm()"></u-modal>
	</view>
</template>

<script>
	import {
		mapState,
		mapGetters
	} from 'vuex'
	export default {
		data() {
			return {
				cancelShow: false,
				loading: true,
				backGround: {
					backgroundColor: 'tranparent',
				},
				orderNumber: '---',
				orderInfo: '',
				payStatusText: '---', //订单支付状态文本
				confirmText: '---', //订单操作按钮确认文本
				cancelText: '----', //订单操作按钮取消文本
				timestamp: 900,
				orderDetail: '',
				orderId: '',
			}
		},
		computed: {
			countDown() {
				let timeRemaining = new Date().getTime() - new Date(this.orderInfo.create_time).getTime();
				return this.timestamp - Math.floor(timeRemaining / 1000)
			}
		},
		onLoad(options) {
			if (options.orderId) this.orderId = options.orderId;
		},
		onShow() {
			let _that = this;
			[_that.payStatusText, _that.confirmText, _that.cancelText] = this.$orderStatusText(_that.orderInfo.status);
			this.getOrderPayInfo();
		},
		methods: {
			confirm() {
				this.$http('/api/order/order_cancel', 'POST', {
						order_id: this.orderInfo.id
					})
					.then(data => {
						this.$message(data.data.msg)
						this.JumpPage(1)
					})
			},
			cancel() {
				this.cancelShow = !this.cancelShow;
			},
			getOrderPayInfo() {
				let _that = this;
				_that.$http('/api/order/order_info', 'POST', {
						order_id: this.orderId
					})
					.then(data => {
						_that.orderInfo = data.data.data
						setTimeout(() => {
							_that.loading = false
						}, 800)
					})
			},
			payNow(orderDetail) {
				let _that = this;
				if (orderDetail.pay_type == 1) {
					_that.$message('余额支付')
				} else {
					uni.getProvider({
						service: 'oauth',
						success: (provider) => {
							// #ifdef MP-ALIPAY
							my.tradePay({
								tradeNO: orderDetail.trade_no,
								success: function(res) {
									console.log(JSON.stringify(res), 'success')
									if (res.resultCode == '6001') {
										_that.$message('已取消支付');
									} else {
										_that.$message('支付成功');
										setTimeout(() => {
											_that.JumpPage('/pages/order-form/index', {},
												1)
										}, 1500)
									}

								},
								fail: function(res) {
									console.log(JSON.stringify(res), 'fail')
								},
							});
							// #endif
							// #ifdef MP-WEIXIN
							_that.$http('/api/order/order_pay','POST',{
								order_id:orderDetail.id
							}).then(res=>{
								uni.requestPayment({
									provider: provider,
									orderInfo: res.data.data,
									...res.data.data,
									success: (res) => {
										if (res.resultCode == '6001') {
											_that.$message('已取消支付');
										} else {
											_that.$message('支付成功');
											_that.$http('/api/order/order_update','POST',{
												order_id:orderDetail.id
											}).then(data=>{
												console.log('通知更改订单状态')
											})
											setTimeout(() => {
												_that.JumpPage('/pages/order-form/index', {},
													1)
											}, 1500)
										}
									},
									fail: (err) => {
										if (~err.errMsg.indexOf('cancel')) {
											_that.$message('已取消支付');
										} else {
											_that.$message('支付失败');
										}
									},
									complete: (data) => {
								
									}
								})
							})
							// #endif
						}
					})
				}
			},
			// 倒计时结束
			timestampEnd(e) {
				console.log(e)
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		min-height: calc(100vh - var(--window-top));
		@include bgImg('@/static/images/header-box-3.png', '#F8F8F8');
		background-color: #F8F8F8;
		padding-bottom: 130rpx;
	}

	.order-status {
		padding: 0 24rpx;
		margin-top: 20rpx;
		box-sizing: border-box;
		color: white;

		.status-text {
			font-weight: bold;
			font-size: 44upx;
		}

		.payment-date {
			font-size: 26upx;
			margin-top: 6rpx;
		}
	}

	.food-cart {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 24rpx;
		padding: 24rpx;
		box-sizing: border-box;
		background-color: white;
		border-radius: 12rpx;

		.shop-name {
			font-size: 30upx;
			font-weight: bold;
			color: #333333;
		}

		.foods {
			.item {
				margin-top: 24rpx;
				display: flex;
				justify-content: space-between;
				align-items: center;

				.left {
					display: flex;

					.img-box {
						image {
							width: 100rpx;
							height: 100rpx;
							border-radius: 4rpx;
						}
					}

					.foods-image-box {
						width: 186rpx;
						height: 186rpx;
					}

					.img-status-text {
						font-size: 24upx;
					}

					.food-info {
						margin-left: 10rpx;

						.food-name {
							color: #333333;
							font-size: 28upx;
							font-weight: bold;
						}

						.food-number {
							margin-top: 24rpx;
							color: #777777;
							font-size: 26upx;
						}
					}
				}

				.price {
					text {
						color: #333333;
					}
				}
			}
		}

		.reality-price {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-top: 32rpx;

			.label {
				font-size: 28upx;
				color: #333333;
				font-weight: bold;
			}

			.reality {
				.unit {
					font-size: 30upx;
				}

				.price {
					font-size: 40upx;
				}
			}

		}
	}

	.order-info {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 24rpx;
		padding: 24rpx;
		box-sizing: border-box;
		border-radius: 12rpx;
		background-color: white;
		color: #333333;

		.title {
			font-size: 32upx;
			font-weight: bold;
		}

		.item {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 24rpx;

			.label {
				font-size: 26upx;
			}

			.value {
				font-size: 28upx;
				font-weight: bold;

				.copy-btn {
					color: #FF7250;
					margin-left: 16rpx;
				}
			}
		}
	}

	.handle-box {
		position: fixed;
		bottom: 0;
		width: 100%;
		height: 120rpx;
		padding: 12rpx;
		box-sizing: border-box;
		background-color: #FFFFFF;
		display: flex;
		align-items: center;
		justify-content: flex-end;

		.btn {
			width: 184rpx;
			height: 64rpx;
			border-radius: 32rpx;

			border: 1px solid #AAAAAA;
			line-height: 64rpx;
			font-size: 30upx;
			color: #777777;
			background-color: white;
			text-align: center;
			margin-left: 40rpx;

			&.confirm {
				background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
				color: white;
				border: none;
			}
		}
	}
</style>
