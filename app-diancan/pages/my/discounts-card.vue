<template>
	<!-- 优惠卡包 -->
	<view class="container">
		<view class="card-box">
			<block v-if="list.length>0">
				<view class="item " :class="{'unusable':item.status!=0}" v-for="(item,index) in list" :key="index">
					<view class="coupon-box">
						<view class="coupon">
							<text class="unit">¥</text> <text class="price">{{item.amount||item.discount_amount}}</text>
						</view>
						<view class="usage">
							满{{item.floor_amount || item.transaction_minimum}}元可用
						</view>
					</view>
					<view class="suitable-object">
						<view class="shop-name">
							{{item.brand_name ||item.stock_name}}
						</view>
						<view class="validity">
							有效期至{{item.expire_date}}
						</view>
					</view>
					<view class="use-btn-box">
						<view class="usable btn" v-if="item.status==0" @click="useDiscounts(item)">
							立即使用
						</view>
						<view class="unusable btn" v-else>
							{{item.status==1?'已使用':'已过期'}}
						</view>
					</view>
				</view>
			</block>
			<block v-else>
				<view class="no-data">
					暂无优惠券
				</view>
			</block>


		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				usable: true, //优惠券可用
				list: [],
				formPage: ''
			}
		},
		onLoad(options) {
			this.formPage = options.page || ''
		},
		onShow() {
			this.getList();
		},
		onUnload() {
			this.formPage = ''
		},
		onReady() {},
		methods: {
			getList() {
				this.$http('/api/coupon/user_coupon', 'POST')
					.then(data => {
						this.list = data.data.data
					})
			},
			useDiscounts(item) {
				if (this.formPage == 'pay') {
					uni.setStorageSync('discountInfo', item);
					setTimeout(() => {
						this.JumpPage(1);
					}, 500)
				} else {
					this.JumpPage('/pages/place-order/index', {}, 1)
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		padding: 24rpx;
		box-sizing: border-box;
		min-height: calc(100vh - var(--window-top));
		background-color: #F8F8F8;
	}

	.card-box {
		.item {
			width: 700rpx;
			height: 200rpx;
			margin: 0 auto;
			margin-top: 24rpx;
			display: flex;
			align-items: center;
			background-color: white;
			border-radius: 12rpx;
			padding: 24rpx;
			box-sizing: border-box;
			@include bgImg('@/static/images/my/card-bg-img.png');
			position: relative;

			.coupon-box {
				text-align: center;

				.coupon {
					.unit {
						font-size: 30upx;
					}

					.price {
						font-size: 44upx;
					}
				}

				.usage {
					font-size: 26upx;
					color: #FF7250;
				}


			}

			.suitable-object {
				text-align: left;
				margin-left: 44rpx;

				.shop-name {
					font-size: 30upx;
					color: #333333;

				}

				.validity {
					color: #777777;
					font-size: 24upx;
					margin-top: 10rpx;
				}
			}

			.use-btn-box {
				display: flex;
				align-items: center;

				.btn {
					width: 150rpx;
					height: 54rpx;
					line-height: 54rpx;
					text-align: center;
					border-radius: 27rpx;
					position: absolute;
					right: 24rpx;
					color: #777777;

					&.usable {
						background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
						color: white;
					}
				}


			}

			&.unusable {
				.coupon {
					text {
						color: #777777;
					}
				}

				.usage {
					color: #AAAAAA;
				}


				.suitable-object {

					.shop-name {
						color: #777777;
					}

					.validity {
						color: #AAAAAA;
					}
				}
			}
		}

	}

	.no-data {
		text-align: center;
		margin-top: 40rpx;
	}
</style>
