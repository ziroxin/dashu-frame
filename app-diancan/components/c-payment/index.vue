<template>
	<view class="c-payment">
		<view class="title u-skeleton-fillet">
			{{title}}
		</view>
		<view class="c-payment-main">
			<view class="c-payment-item u-skeleton-fillet" v-for="(item,index) in paymentList" :key="index"
				@click="choosePay(item.provider)">
				<view class="item-info">
					<image :src="item.icon" mode="widthFix"></image>
					<text>{{item.text}}</text>
				</view>
				<view class="choose" :class="{'active':pay == item.provider}">
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'c-payment',
		props: {
			hideType: String,
			title: {
				type: String,
				default: '支付方式'
			}
		},
		data() {
			return {
				pay: 'my', //my: 余额支付 alipay: 支付宝 wechat：微信支付
				paymentList: [
					{
						provider: 'my',
						text: '余额支付',
						icon: require('@/static/images/place-order/icon-yue.png')
					},
					// #ifdef APP-PLUS,H5
					{
						provider: 'wechat',
						text: '微信支付',
						icon: require('@/static/images/place-order/icon-wechat.png')
					},
					{
						provider: 'alipay',
						text: '支付宝',
						icon: require('@/static/images/place-order/icon-alipay.png')
					}
					// #endif
					// #ifdef MP-WEIXIN
					{
						provider: 'wechat',
						text: '微信支付',
						icon: require('@/static/images/place-order/icon-wechat.png')
					},
					// #endif
					// #ifdef MP-ALIPAY
					{
						provider: 'alipay',
						text: '支付宝',
						icon: require('@/static/images/place-order/icon-alipay.png')
					}
					// #endif
				]
			}
		},
		mounted() {
			if (this.hideType) {
				this.paymentList.map((item, idx) => {
					if (this.hideType == item.provider) {
						this.paymentList.splice(idx, 1);
						this.pay = this.paymentList[0].provider;
					}
				})
			}
			uni.$on('defaultPayType',()=>{
				this.pay = 'my';
			})
		},
		methods: {
			// 选择支付类型
			choosePay(type) {
				this.pay = type;
				this.$emit('choosePay', this.pay);
			},
		}
	}
</script>

<style lang="scss" scoped>
	.c-payment {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 24rpx;
		padding: 24rpx;
		box-sizing: border-box;
		background-color: white;
		border-radius: 12rpx;

		.title {
			font-size: 30upx;
			color: #333333;
			font-weight: bold;
			margin-bottom: 44rpx;
		}

		.c-payment-main {
			.c-payment-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-bottom: 44rpx;

				&:last-child {
					margin-bottom: 0;
				}

				.item-info {
					display: flex;
					align-items: center;

					image {
						width: 44rpx;
						height: 44rpx;
						margin-right: 10rpx;
					}

					text {
						font-size: 28upx;
						color: #333333;
					}
				}

				.choose {
					width: 40rpx;
					height: 40rpx;
					border: 2px solid #AAAAAA;
					border-radius: 50%;

					&.active {
						@include bgImg('@/static/images/place-order/choose.png');
						border: none;
					}
				}
			}
		}
	}
</style>
