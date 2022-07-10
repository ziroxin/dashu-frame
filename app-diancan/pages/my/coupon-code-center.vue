<template>
	<!-- 领券中心 -->
	<view class="container">
		<mescroll-body class="mescroll-box" ref="mescrollRef" @down="downCallback" @up="upCallback">
			<block v-if="discountCouponList.length>0">
				<!-- #ifdef MP-ALIPAY -->
				<view class="item" v-for="(item,index) in discountCouponList" :key="index"
					@click="getDiscountCoupon(item)">
					<view class="merchant-info">
						
					</view>
				</view>

				<!-- #endif -->
				<!-- #ifdef MP-WEIXIN -->
				<view class="item-box" v-for="(item,index) in discountCouponList" :key="index">
					<send-coupon @customevent="getcoupon"
						:send_coupon_params="[{stock_id:item.stock_id,out_request_no:item.out_request_no}]"
						:sign="item.sign" :send_coupon_merchant="item.belong_merchant">
						<view class="item">
							<view class="merchant-info">
							
							</view>
						</view>
					</send-coupon>
				</view>
				<!-- #endif -->
			</block>
			<block v-else>
				<view class="no-data">
					暂无优惠券
				</view>
			</block>
		</mescroll-body>
		<u-loadmore :status="status" />
	</view>
</template>

<script>
	import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
	export default {
		mixins: [MescrollMixin],
		data() {
			return {
				mescroll: null,
				discountCouponList: [],
				status: 'loadmore',
				page: {
					num: 1
				},
				total: 0,
				send_coupon_params: '',
				sign: '',
				send_coupon_merchant: '',
			}
		},
		onReachBottom() {
			if (this.discountCouponList.length >= this.total) return;
			this.status = 'loading';
			this.page['num'] += 1;
			this.getDiscountCouponList(this.page);
		},
		onLoad() {

		},
		onShow() {
			this.page['num'] = 1
			this.getDiscountCouponList(this.page);
		},
		onReady() {

		},
		methods: {
			getcoupon(params) {
				let _that = this;
				console.log('领取', params)
				if (params.detail.errcode == 'OK') {
					uni.showToast({
					    title: '社区版暂不支持',
						content:'需要支付功能，请联系作者购买商业版',
					    duration: 5000
					});
					_that.$message('已领取过该优惠券')
				} else {
					_that.$message(params.detail.msg)
				}
			},
			getDiscountCouponList(page) {
				uni.showToast({
					title: '社区版暂不支持',
					content:'需要支付功能，请联系作者购买商业版',
					duration: 5000
				});
				
			},
			downCallback(mescroll) {
				this.page['num'] = 1
				this.getDiscountCouponList(this.page);
				// if (this.mescroll) {
				// 	this.mescroll.resetUpScroll();
				// }
			},

			// 领取
			getDiscountCoupon(item) {
				uni.showToast({
				    title: '社区版暂不支持',
					content:'需要支付功能，请联系作者购买商业版',
				    duration: 5000
				});

			},
		}
	}
</script>

<style lang="scss" scoped>
	.item {
		width: 702rpx;
		height: 148rpx;
		margin: 24rpx auto;
		padding: 24rpx;
		box-sizing: border-box;
		border-radius: 12rpx;
		background-color: white;
		display: flex;
		justify-content: space-between;
		align-items: center;

		.merchant-info {
			height: 100rpx;
			display: flex;
			align-items: center;

			.img-box {
				width: 100rpx;
				height: 100rpx;
				margin-right: 20rpx;
			}

			.info {
				height: 100rpx;

				.brand-name {
					font-size: 28upx;
					color: #333333;
					font-weight: bold;
				}

				.validity {
					font-size: 26upx;
					color: #333333;
					margin-top: 22rpx;
				}
			}
		}

		.handle {
			height: 100rpx;

			.value {
				font-size: 36upx;
				text-align: center;
			}

			.btn {
				width: 120rpx;
				height: 40rpx;
				border-radius: 6rpx;
				text-align: center;
				line-height: 40rpx;
				color: #FF7250;
				background-color: #FFF3F1;
				font-size: 26upx;
				margin-top: 10rpx;

				&.received {
					color: white;
					background-color: rgb(172, 169, 172);

				}
			}
		}
	}

	.no-data {
		text-align: center;
		margin-top: 40rpx;
	}
</style>
