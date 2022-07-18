<template>
	<!-- 订单 -->
	<view class="container u-skeleton">


		<!-- 吸顶 -->
		<u-sticky :enable='enable'>
			<view class="tabs-box">
				<view class="btn">
				<navigator url="/pages/place-order/index" open-type="switchTab" hover-class="other-navigator-hover">
					<button class="mini-btn" type="primary" size="mini">加菜</button>
				</navigator>
				</view>
				
				<view class="btn">
				<navigator url="navigate/navigate?title=navigate" hover-class="navigator-hover">
					<button class="mini-btn" type="warn" size="mini">结账</button>
				</navigator>
				</view>
				
				<view class="btn">
				<navigator :url="'/pages/order-form/order-bill?orderDetailList='+encodeURIComponent(JSON.stringify(orderDetailList[0]))"
					hover-class="navigator-hover">
					<button class="mini-btn" type="default" size="mini">开发票</button>
				</navigator>
				</view>
			</view>
		</u-sticky>

		<mescroll-body ref="mescrollRef" class="data-list-box" @init="mescrollInit" @up="upCallback" :down="downOption"
			@down="downCallback">
			<block v-if="orderDetailList.length>0">
				<view class="food-cart">
					<view class="foods">
						<view class="item u-skeleton-fillet" v-for="(item,index) in orderDetailList" :key="index">
							<view class="left">
								<view class="img-box ">
									<u-image class="foods-image-box u-skeleton-fillet" width="100" height="100"
										:src="imgUrl+item.picture">
										<view class="img-status-text" slot="loading">
											加载中
										</view>
										<view class="img-status-text" slot="error">
											加载失败
										</view>
									</u-image>
								</view>
								<view class="food-info">
									<view class="food-name ">
										{{item.dishesName}}
									</view>
									<view class="food-number ">
										{{item.dishesNumber}}
									</view>
								</view>
							</view>
							<view class="price ">
								<text class="unit">¥</text><text class="price">{{item.dishesAmount}}</text>
							</view>
						</view>
					</view>
				</view>
			</block>
			<block v-else>
				<view class="not-data" style="margin-top: 150rpx;">
					<view class="img-box">
						<image src="@/static/images/no-data.png" mode="widthFix"></image>
					</view>
					<text>暂无数据</text>
				</view>
			</block>
		</mescroll-body>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
	export default {
		mixins: [MescrollMixin],
		data() {
			return {
				orderDetailList: [],
				oauthShow: false,
				cancelVal: false,
				loading: true,
				downOption: {
					auto: false,
				},
				enable: false,
				modelTitle: '',
				modelContent: '',
				cancelText: '',
				orderId: '',
				status: 'loadmore',
				page: {
					num: 1
				},
				total: 0,
				imgUrl: this.$imgURL
			}
		},
		// 上拉加载
		onReachBottom() {
			console.log("on reach bottom");
			if (this.orderList.length >= this.total) return;
			this.status = 'loading';
			this.page['num'] += 1;
			this.getOrderList();
		},
		computed: {
			...mapState(['selectedShop', 'orderList'])
		},
		onShow() {
			this.enable = true;
			this.page['num'] = 1
			this.getOrderList();
		},
		onHide() {
			this.enable = false;
		},
		onReady() {
			// this.isOAuth();
		},
		methods: {
			getOrderList() {
				let _that = this
				_that.$http('/can/api/open/orderDetail/list', 'GET', {
					orderId: '6163e00e-2eaa-4996-93c9-6d62109e367c',
				}).then(data => {
					console.log(data);
					this.orderDetailList = data.data.data
					this.total = data.data.data.total;
					_that.mescroll.endBySize(10, data.data.data.total);
				}).catch((err) => {
					console.log(err, "errorziro");
				})


			},
			cancel(obj) {
				this.cancelVal = !this.cancelVal;
				this.modelTitle = obj.cancelText;
				this.modelContent = '是否' + obj.cancelText + '？';
				this.cancelText = obj.cancelText
				this.orderId = obj.orderId
			},
			// tabs通知swiper切换
			tabsChange(index) {
				this.swiperCurrent = index;
				this.current = index;
				this.getOrderList(this.page);
			},
			// swiper-item左右移动，通知tabs的滑块跟随移动
			transition(e) {
				let dx = e.detail.dx;
				this.$refs.uTabs.setDx(dx);
			},
			// 由于swiper的内部机制问题，快速切换swiper不会触发dx的连续变化，需要在结束时重置状态
			// swiper滑动结束，分别设置tabs和swiper的状态
			animationfinish(e) {
				let current = e.detail.current;
				this.$refs.uTabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},

			// mescrollInit(mescroll) {
			// 	this.mescroll = mescroll;
			// },
			downCallback(mescroll) {

				this.page['num'] = 1
				this.getOrderList(this.page);
				// this.$nextTick(function() {
				// 	this.mescroll.resetUpScroll();
				// })
			},
			// // TODO
			// upCallback(page) {
			// 	// console.log(page.num,this.orderList)
			// 	let _that = this,
			// 		oldList = [..._that.orderList],
			// 		newList = [],
			// 		judge = typeof this.$store.state.oauthInfo === 'string' ? this.$store.state.oauthInfo.indexOf(
			// 			'loginCode') : this.$store.state.oauthInfo.hasOwnProperty('loginCode'),
			// 		status = _that.swiperCurrent == 0 ? 99 : _that.swiperCurrent == 1 ? 0 : _that.swiperCurrent == 2 ?
			// 		1 : _that.swiperCurrent == 3 ? -1 : 55;
			// 	_that.$http('/api/order/order_list', 'POST', {
			// 		shop_id: _that.$store.state.shopId,
			// 		status: status,
			// 		pageSize: 4,
			// 		page: page.num
			// 	}).then(data => {
			// 		if (data.data.code == 1) {
			// 			if (page.num == 1) {
			// 				oldList = []
			// 			}
			// 			newList = oldList.concat(data.data.data.data)
			// 			if (judge == 0 || judge) {
			// 				_that.$store.commit('SET_ORDER_LIST', newList)
			// 			} else {
			// 				_that.$store.commit('SET_ORDER_LIST', [])
			// 			}
			// 			_that.mescroll.endBySize(4, data.data.data.total);
			// 			setTimeout(() => {
			// 				this.$nextTick(function() {
			// 					this.loading = false;
			// 				})
			// 			}, 1000)
			// 		} else {
			// 			setTimeout(() => {
			// 				this.$nextTick(function() {
			// 					this.loading = false;
			// 				})
			// 			}, 1000)
			// 		}
			// 	}).catch(() => {
			// 		setTimeout(() => {
			// 			this.$nextTick(function() {
			// 				this.loading = false;
			// 			})
			// 		}, 1000)
			// 	})
			// },

			orderDishes() {
				uni.switchTab({
					url: '/pages/place-order/index'
				});
				// 跳到订单页
				// this.JumpPage('/pages/place-order/index')
			}

		}
	}
</script>

<style lang="scss" scoped>
	/*每个页面公共css */
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "uview-ui/index.scss";


	.btn {
		margin-top: 5%;
		margin-left: 10%;
	}

	.tabs-box {
		width: 100%;
		background-color: white;
		display: flex;
	}

	.data-list-box {
		height: auto;
		overflow: auto;
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

						.img-status-text {
							font-size: 24upx;
						}
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

		.choose-coupon {
			width: 100%;
			height: 60rpx;
			line-height: 60rpx;
			margin: 12rpx auto;
			display: flex;
			justify-content: space-between;
			align-items: center;
			font-size: 28upx;
			color: #333333;
			font-weight: bold;

			.value {
				.no-coupon {
					font-weight: normal;
					color: #999999;
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
</style>