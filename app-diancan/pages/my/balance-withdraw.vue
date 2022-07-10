<template>
	<!-- 提现 -->
	<view class="container">
		<!-- #ifndef MP-ALIPAY -->
		<u-navbar back-icon-color="white" class="navbar" title-color="white" title="提现" :background="backGround"
			:isFixed="false">
		</u-navbar>
		<!-- #endif -->
		<!-- #ifdef MP-ALIPAY -->
		<u-navbar class="navbar" title-color="white" :background="backGround" :is-back="false" :isFixed="false">
		</u-navbar>
		<!-- #endif -->

		<view class="assets-box">
			<view class="can-withdraw item">
				<view class="label">
					可提现金额
				</view>
				<view class="price-box">
					<text class="unit">¥</text><text class="price">958.00</text>
				</view>
			</view>
			<view class="audit item">
				<view class="label">
					审核中
				</view>
				<view class="price-box">
					<text class="unit">¥</text><text class="price">130.00</text>
				</view>
			</view>
		</view>

		<view class="withdraw-handle">
			<view class="title">
				提现金额
			</view>
			<view class="price-box">
				<input type="number" class="recharge-input" v-model="price" />
				<text class="all" @click="">全部提现</text>
			</view>
			<view class="fee">
				手续费1.3元
			</view>
		</view>

		<view class="withdraw-to">
			<view class="label">
				提现至
			</view>
			<view class="item">
				<image src="@/static/images/my/icon-wechat.png" mode="widthFix"></image>
				微信 >
			</view>
		</view>

		<view class="btn">
			申请提现
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				backGround: {
					backgoundColor: 'transparent',
				},
				price: 0
			}
		},
		onLoad() {

		},
		onShow() {

		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		min-height: calc(100vh - var(--window-top));
		@include bgImg('@/static/images/header-box-3.png', '#F8F8F8');
		background-color: #F8F8F8;
	}

	.assets-box {
		display: flex;
		align-items: center;
		justify-content: space-around;
		margin-top: 40rpx;

		.item {
			text-align: center;

			.label {
				font-size: 26upx;
				color: white;
			}

			.price-box {

				.unit {
					font-size: 30upx;
					color: white;
				}

				.price {
					margin-top: 12rpx;
					font-size: 60upx;
					color: white;
				}
			}
		}
	}

	.withdraw-handle {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 24rpx;
		padding: 24rpx;
		box-sizing: border-box;
		background-color: white;

		.title {
			font-size: 30upx;
			color: #333333;
			font-weight: bold;
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

			.all {
				white-space: nowrap;
				font-size: 26upx;
				color: #FF7250;
			}
		}

		.fee {
			margin-top: 18rpx;
			font-size: 24upx;
			color: #AAAAAA;
		}
	}

	.withdraw-to {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 24rpx;
		padding: 24rpx;
		border-radius: 12rpx;
		box-sizing: border-box;
		display: flex;
		justify-content: space-between;
		background-color: white;
		font-size: 28upx;
		color: #333333;

		.item {
			display: flex;
			align-items: center;

			image {
				width: 40rpx;
				height: 40rpx;
				margin-right: 10rpx;
			}
		}
	}

	.btn {
		width: 700rpx;
		height: 88rpx;
		margin: 0 auto;
		margin-top: 120rpx;
		border-radius: 12rpx;
		background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
		line-height: 88rpx;
		text-align: center;
		color: white;
		font-size: 30upx;
	}
</style>
