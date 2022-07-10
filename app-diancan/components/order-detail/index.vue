<template>
	<view class="order-detail">
		<view class="header-box u-skeleton-fillet">
			<view class="order-info">
				<view class="dining-type" v-if="orderInfo.type==2">
					桌号单
				</view>
				<view class="dining-type" v-else>
					自取单
				</view>
				<view class="name">
					{{orderInfo.name}}
				</view>
			</view>
			<view class="order-status">
				{{orderInfo.status_text}}
			</view>
		</view>
		<view class="main">
			<scroll-view class="order-rate-container" scroll-x="true">
				<view class="order-container">
					<block v-if="list.length>0">
						<block v-if="list.length>1">
							<view class="detail" v-for="(items,index) in list" :key="index">
								<u-image class="foods-image-box u-skeleton-fillet" :src="items.img" width="120rpx"
									height="110rpx" border-radius="12rpx">
									<view class="img-status-text" slot="loading">
										加载中
									</view>
									<view class="img-status-text" slot="error">
										加载失败
									</view>
								</u-image>
								<text class="u-skeleton-fillet">{{items.goods_name}}</text>
							</view>
						</block>
						<block v-else>
							<view class="detail alone" v-for="(items,index) in list" :key="index">
								<u-image class="foods-image-box u-skeleton-fillet" :src="items.img" width="120rpx"
									height="110rpx" border-radius="12rpx">
									<view class="img-status-text" slot="loading">
										加载中
									</view>
									<view class="img-status-text" slot="error">
										加载失败
									</view>
								</u-image>
								<text class="u-skeleton-fillet">{{items.goods_name}}</text>
							</view>
						</block>
					</block>
					<block v-else>
						<view class="detail single u-skeleton-fillet">
							<u-image class="foods-image-box u-skeleton-fillet" src="@/static/images/test.png"
								width="120rpx" height="110rpx" border-radius="12rpx"></u-image>
							<text>烙馍(5张)</text>
						</view>
					</block>

				</view>
			</scroll-view>
			<view class="order-number ">
				<text class="u-skeleton-fillet">共{{orderInfo.count}}件</text>
			</view>
		</view>
		<!-- <view class="take-food-info u-skeleton-fillet" v-if="orderInfo.type==1">
			取餐信息：{{orderInfo.username}} {{orderInfo.userphone}} {{orderInfo.createTime}}
		</view> -->
		<view class="other-box u-skeleton-fillet">
			<!-- <view class="date">
				下单时间：{{orderInfo.createTime}}
			</view> -->
			<view class="amount">
				合计：<text class="unit">¥</text><text class="price">
					{{orderInfo.price}}
				</text>
			</view>
		</view>
		<view class="handle-box">
			<view class="other-use btn" v-if="cancelText!=''" @click.stop="cancel(cancelText)">
				{{cancelText}}
			</view>
			<view class="confirm btn" v-if="confirmText!=''" @click.stop="paymentOrder(confirmText)">
				{{confirmText}}
			</view>
		</view>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	export default {
		name: "order-detail",
		props: {
			orderInfo: {
				type: Object,
				default: () => {
					return {
						type: 1, //订单类型
						shopName: '----', //商家名字
						payStatus: '1', //付款状态
						createTime: '2021-04-18 12:30',
						"foods": []
					}
				}
			},
			payStatus: {
				type: [String, Number],
				default: 1
			},

		},
		data() {
			return {
				orderRateList: 1,
				payStatusText: '---', //订单支付状态文本
				confirmText: '---', //订单操作按钮确认文本
				cancelText: '----' //订单操作按钮取消文本
			};
		},
		computed: {
			list() {
				return JSON.parse(this.orderInfo.goods_sku)
			}
		},
		watch: {
			payStatus: {
				handler(oldVal, newVal) {
					let _that = this;
					[_that.payStatusText, _that.confirmText, _that.cancelText] = this.$orderStatusText(_that.payStatus);
				},
				deep: true
			}
		},
		mounted() {
			let _that = this;
			[_that.payStatusText, _that.confirmText, _that.cancelText] = this.$orderStatusText(_that.payStatus);
		},
		methods: {
			paymentOrder(confirmText) {
				if (confirmText == '确认收货') {
					console.log('确认收货')
				} else if (confirmText == '去支付') {
					this.JumpPage('/pages/order-form/order-details', {
						orderId: this.orderInfo.id
					})
				}
			},
			cancel(cancelText) {
				this.$emit('cancel', {
					cancelText: cancelText,
					orderId: this.orderInfo.id
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.order-detail {
		width: 700rpx;
		// min-height: 262rpx;
		margin: 0 auto;
		margin-top: 24rpx;
		padding: 20rpx;
		box-sizing: border-box;
		border-radius: 12rpx;
		background-color: white;

		.header-box {
			display: flex;
			justify-content: space-between;
			align-items: center;
			border-bottom: 1rpx solid #EEEEEE;
			margin-bottom: 20rpx;
			padding-bottom: 20rpx;
			box-sizing: border-box;

			.order-info {
				display: flex;
				justify-content: space-between;
				align-items: center;

				.dining-type {
					min-width: 92rpx;
					height: 46rpx;
					border-radius: 8rpx;
					background-color: #FFE2E2;
					font-size: 24upx;
					color: #F65D5D;
					font-weight: bold;
					text-align: center;
					line-height: 46rpx;
				}

				.name {
					width: 430rpx;
					font-size: 28upx;
					color: #333333;
					margin-left: 24rpx;
					font-weight: bold;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
					display: block;
				}
			}

			.order-status {
				font-size: 26upx;
				color: #777777;
			}
		}

		.main {
			display: flex;
			align-items: center;
			// position: relative;

			.order-rate-container {
				width: 100%;

				.order-container {
					width: 100%;
					display: flex;
					align-items: center;
					overflow: hidden;

					.detail {
						margin-right: 24rpx;
						text-align: center;

						&:last-child {
							margin-right: 0;
						}

						.foods-image-box {
							width: 120rpx;
							height: 110rpx;
							border-radius: 12rpx;
							flex-shrink: 0;
						}

						.img-status-text {
							font-size: 26upx;
							white-space: nowrap;
						}

						/deep/ image {
							width: 120rpx;
							height: 110rpx;
							border-radius: 12rpx;
							flex-shrink: 0;
						}

						text {
							font-size: 22upx;
							color: #777777;
							white-space: nowrap;
							display: block;
							width: 120rpx;
							overflow: hidden;
							text-overflow: ellipsis;
							margin-top: 20rpx;
						}

						&.alone {
							display: flex;
							align-items: center;

							text {
								font-size: 26upx;
								margin-left: 20rpx;
							}
						}

						&.single {
							display: flex;
							align-items: center;

							text {
								width: calc(100% - 120rpx);
								font-size: 26upx;
								margin-left: 20rpx;
							}
						}
					}
				}

			}

			.order-number {
				// position: absolute;
				right: 0;
				top: 0;
				background-color: rgba($color: #FFFFFF, $alpha: 0.9);
				width: 120rpx;
				height: 100%;
				padding-bottom: 46rpx;
				font-size: 24upx;
				color: #777777;
				text-align: center;
				line-height: 110rpx;
			}
		}

		.take-food-info {
			font-size: 24upx;
			color: #333333;
			margin: 16rpx 0;
		}

		.other-box {
			display: flex;
			align-items: center;
			justify-content: space-between;
			margin-top: 24rpx;

			view {
				font-size: 24upx;
				color: #777777;
			}

			.date {}

			.amount {
				.unit {
					font-weight: bold;
					color: #FF7250;
				}

				.price {
					font-size: 30upx;
					font-weight: bold;
					color: #FF7250;

				}
			}
		}

		.handle-box {
			display: flex;
			justify-content: flex-end;
			margin-top: 20rpx;

			.btn {
				width: 128rpx;
				height: 48rpx;
				font-size: 26upx;
				text-align: center;
				line-height: 48rpx;
				border-radius: 8rpx;
				border: 1px solid #AAAAAA;
				color: #777777;
				font-weight: bold;
				margin-left: 40rpx;

				&.confirm {
					color: #FF7250;
					border: 1px solid #FF7250;
				}
			}
		}
	}
</style>
