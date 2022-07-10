<template>
	<!-- 余额充值 -->
	<view class="container">
		<!-- <view class="custom-recharge">
			<view class="title">
				余额充值（元）
			</view>
			<view class="price-box">
				<input type="number" class="recharge-input" v-model="price" />
				<text class="give">赠送{{giveNumber}}元</text>
			</view>
		</view> -->

		<view class="fixation-recharge">
			<view class="title">
				请选择充值金额（元）
			</view>
			<view class="recharge-box">
				<view class="recharge-item" v-for="(item,index) in rechargeList" :key="index"
					:class="{'active':currentRecharge == index}" @click="changeRecharge(item,index)">
					<view class="choose-icon">
					</view>
					<view class="recharge-number">
						{{item.money||0}}
					</view>
					<view class="give-number" v-show="item.discount_money">
						送{{item.discount_money||0}}
					</view>
				</view>
			</view>
		</view>

		<view class="payment-type">
			<cpayment @choosePay="paymentType" hideType="my" title="充值方式"></cpayment>
			<view class="tip">
				确认充值即表示阅读并同意 <text class="recharge-agreement" @click="agreement">充值协议</text>
			</view>
		</view>

		<view class="btn" @click="balanceRecharge">
			去充值
		</view>
	</view>
</template>

<script>
	import cpayment from '@/components/c-payment/index.vue'
	export default {
		components: {
			cpayment
		},
		data() {
			return {
				// 充值金额列表
				rechargeList: [],
				price: 0, //充值数量
				giveNumber: '3.00', // 赠送数量
				currentRecharge: 0,
				payType: 2,
				id: '1',
			}
		},
		onLoad() {

		},
		onShow() {
			this.getRechargeList()
		},
		methods: {
			agreement(){
				this.JumpPage('/pages/my/agreement-page')
			},
			getRechargeList() {
				this.$http('/api/assets/assets_list', 'GET')
					.then(data => {
						this.rechargeList = data.data.data
					})
			},
			// 选择充值数量
			changeRecharge(item, index) {
				this.id = item.id;
				this.currentRecharge = index;
			},
			// 余额充值方式
			paymentType(pay) {
				if (pay == 'alipay') {
					this.payType = 2;
				} else if (pay == 'wechat') {
					this.payType = 3;
				}
			},
			// 余额充值
			balanceRecharge() {
				this.$http('/api/assets/recharge', 'POST', {
						id: this.id,
						pay_type: this.payType,
						shop_id: this.$store.state.shopId
					})
					.then(data => {
						this.rechargeNow(data.data.data.trade_no)
					})
			},
			// 充值
			rechargeNow(tradeNo) {
				let _that = this;
				uni.getProvider({
					service: 'oauth',
					success: (provider) => {
						uni.requestPayment({
							provider: provider,
							orderInfo: tradeNo,
							success: (res) => {
								if (res.resultCode == "6001") {
									_that.$message('已取消支付');
								} else {
									_that.$message('支付成功');
									setTimeout(() => {
										_that.JumpPage(1)
									}, 1500)
								}
							},
							fail: (err) => {
								_that.$message('支付失败');
								console.log(err)
							}
						})
					}
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		min-height: calc(100vh - var(--window-top));
		padding: 24rpx;
		box-sizing: border-box;
		background-color: white;
	}

	.custom-recharge {
		.title {
			font-size: 32upx;
			font-weight: bold;
			color: #333333;
		}

		.price-box {
			height: 82rpx;
			margin-top: 12rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			border-bottom: 1px solid #EEEEEE;

			.recharge-input {
				height: 82rpx;
				line-height: 82rpx;
				font-size: 60upx;
				color: #FF7250;
			}

			.give {
				white-space: nowrap;
				font-size: 26upx;
				color: #FF7250;
			}
		}
	}

	.fixation-recharge {
		margin-top: 40rpx;

		.title {
			font-size: 32upx;
			font-weight: bold;
			color: #333333;
		}

		.recharge-box {
			display: flex;
			flex-wrap: wrap;
			justify-content: space-between;
			align-items: center;

			.recharge-item {
				width: 220rpx;
				height: 108rpx;
				margin-top: 24rpx;
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				border-radius: 12rpx;
				border: 1px solid #AAAAAA;
				position: relative;

				&.active {
					border: 1px solid #FF7250;

					.choose-icon {
						width: 56rpx;
						height: 32rpx;
						position: absolute;
						top: 0;
						right: 0;
						@include bgImg('@/static/images/my/recharge-choose.png');
					}
				}

				.recharge-number {
					font-size: 30upx;
					color: #333333;
				}

				.give-number {
					font-size: 22upx;
					color: #FF7250;
					margin-top: 4rpx;
				}
			}
		}
	}

	.payment-type {
		/deep/ .c-payment {
			padding: 0;
		}

		.tip {
			margin-top: 18rpx;
			font-size: 24upx;
			color: #333333;

			.recharge-agreement {
				color: #FF7250;
			}
		}
	}

	.btn {
		width: 700rpx;
		height: 88rpx;
		line-height: 88rpx;
		text-align: center;
		font-size: 30upx;
		color: white;
		border-radius: 12rpx;
		background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
		margin-top: 100rpx;
	}
</style>
