<template>
	<!-- 扫码支付 -->
	<view class="container">
		<!-- #ifndef MP-ALIPAY -->
		<u-navbar back-icon-color="white" class="navbar" title-color="white" title="支付" :background="backGround"
			:isFixed="false">
		</u-navbar>
		<!-- #endif -->
		<!-- #ifdef MP-ALIPAY -->
		<u-navbar class="navbar" title-color="white" :background="backGround" :is-back="false" :isFixed="false">
		</u-navbar>
		<!-- #endif -->
		<view class="header-box u-skeleton-rect" @click="JumpPage('/pages/home/changes-stores')">
			<view class="image-box">
				<image src="@/static/images/test.png" mode="widthFix"></image>
				<view class="operating-status" :class="{'ing':operating}">
					{{operating?'营业中':'未营业'}}
				</view>
			</view>
			<view class="store-detail">
				<view class="title">
					<view class="store-name">
						烙馍村家常菜（红旗路店）
					</view>
					<view class="change-btn">
						<view class="change-text">
							切换
						</view>
						<image src="@/static/images/home/change-btn.png" mode="widthFix"></image>
					</view>
				</view>
				<view class="sales-volume">
					<view class="number-box">
						月售7809
					</view>
					<view class="volume-status" :class="{'give':false}">
						{{false?'可外送':'可自取'}}
					</view>
				</view>
				<view class="address-box">
					<image src="@/static/images/home/addr-img.png" mode="widthFix"></image>
					<view class="address-detail">
						郑州市金水区丰产路红旗路XXX分店
					</view>
					<view class="distance">
						小于 300m </view>
				</view>
			</view>
		</view>

		<view class="price-boxs">
			<view class="label">
				支付金额
			</view>
			<view class="price-box">
				<text class="unit">¥</text><text class="price">130.00</text>
			</view>
		</view>

		<view class="pay-type">
			<cpayment @choosePay="paymentType"></cpayment>
		</view>

		<view class="btn" @click="balanceRecharge">
			去支付
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
				backGround: {
					backgoundColor: 'transparent',
				},
				operating: false,
			}
		},
		onLoad() {

		},
		onShow() {

		},
		methods: {
			paymentType(pay) {

			},
			balanceRecharge() {},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		min-height: 100vh;
		@include bgImg('@/static/images/my/header-box-1.png', '#FFFFFF');
		background-color: white;
	}

	.header-box {
		width: 700rpx;
		height: 184rpx;
		margin: 0 auto;
		margin-top: 40rpx;
		border-radius: 12rpx;
		padding: 20rpx;
		box-sizing: border-box;
		background-color: #FFFFFF;
		display: flex;
		box-shadow: 2rpx 2rpx 8rpx 0px rgba($color: #5A4239, $alpha: .2);

		.image-box {
			width: 144rpx;
			height: 144rpx;
			overflow: hidden;
			border-radius: 6rpx;
			position: relative;
			flex-shrink: 0;
			margin-right: 16rpx;

			image {
				width: 144rpx;
				height: 144rpx;

			}

			.operating-status {
				min-width: 70rpx;
				height: 32rpx;
				line-height: 32rpx;
				position: absolute;
				top: 0;
				right: 0;
				font-size: 20upx;
				color: white;
				white-space: nowrap;
				background-color: #FF7250;
				border-radius: 0px 0px 0px 9px;
				padding: 0 5rpx;
				box-sizing: border-box;
			}
		}

		.store-detail {
			width: calc(100% - 164rpx);

			view {
				display: flex;
			}

			.title {
				justify-content: space-between;

				.store-name {
					font-size: 30upx;
					font-weight: bold;
					color: #333333;
				}

				.change-btn {
					display: flex;
					align-items: center;
					color: #777777;

					image {
						width: 32rpx;
						height: 32rpx;
					}

				}
			}
		}

		.sales-volume {
			margin: 16rpx 0;

			.number-box {
				font-size: 26upx;
				color: #777777;
				margin-right: 20rpx;
			}

			.volume-status {
				min-width: 76rpx;
				height: 36rpx;
				line-height: 36rpx;
				padding: 0 5rpx;
				box-sizing: border-box;
				border-radius: 6rpx;
				background-color: #FFE6DD;
				color: #FF7250;
				font-size: 24upx;

				&.give {
					background-color: #D9F2FF;
					color: #2791FF;

				}
			}
		}

		.address-box {
			align-items: center;
			overflow: hidden;
			font-size: 24upx;
			color: #777777;

			image {
				width: 30rpx;
				height: 30rpx;
				margin-right: 12rpx;
			}

			.address-detail {
				display: inline-block;
				width: 320rpx;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				margin-right: 50rpx;
			}

			.distance {
				white-space: nowrap;
			}
		}
	}

	.price-boxs {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 34rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;

		.label {
			font-size: 28upx;
			font-weight: bold;
			color: #333333;
		}

		.price-box {

			.unit {
				font-size: 28upx;
			}

			.price {
				font-size: 44upx;
			}
		}
	}

	.pay-type {
		margin-top: 56rpx;

		/deep/ .c-payment {
			padding: 0;
		}
	}

	.btn {
		width: 700rpx;
		height: 88rpx;
		line-height: 88rpx;
		margin: 0 auto;
		text-align: center;
		font-size: 30upx;
		color: white;
		border-radius: 12rpx;
		background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
		margin-top: 100rpx;
	}
</style>
