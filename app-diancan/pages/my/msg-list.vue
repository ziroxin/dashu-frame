<template>
	<!-- 站内消息 -->
	<view class="container">
		<mescroll-body ref="mescrollRef" @down="downCallback" @up="upCallback">
			<view class="msg-item" v-for="(item,index) in 3" :key="index">
				<view class="icon-box">
					<image src="@/static/images/my/icon-await.png" mode="widthFix"></image>
				</view>
				<view class="detail">
					<view class="head-box">
						<view class="title">
							商品派送通知
						</view>
						<view class="date">
							2021-4-19 18:00
						</view>
					</view>
					<view class="main">
						您的订单号为1948590708988765的商品，正由商家【烙馍村家常菜】派送中，请注意查收。
					</view>
				</view>
			</view>
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
				noticeList: [],
				status: 'loadmore',
				page: {
					num: 1
				},
				total: 0,
			}
		},
		onReachBottom() {
			if (this.noticeList.length >= this.total) return;
			this.status = 'loading';
			this.page['num'] += 1;
			this.getNotice(this.page);
		},
		onLoad() {

		},
		onShow() {
			this.page['num'] = 1
			this.getNotice(this.page);
		},
		methods: {
			getNotice(page) {
				let _that = this,
					oldList = [..._that.noticeList],
					newList = [];
				this.$http('/api/common/bulletin', 'POST', {
						shop_id: _that.$store.state.shopId,
						page: page.num,
						pageSize: 10
					})
					.then(data => {
						if (page.num == 1) {
							oldList = []
						}
						_that.total = data.data.data.total;
						_that.noticeList = oldList.concat(data.data.data.data);
						if (_that.noticeList.length >= data.data.data.total) {
							_that.status = 'nomore';
						}
					})
				setTimeout(() => {
					this.mescroll.endErr()
				}, 1500)
			},
			downCallback(mescroll) {
				this.page['num'] = 1
				this.getNotice(this.page);
				// mescroll.resetUpScroll();
			},
			// upCallback(page) {
			// 	let _that = this,
			// 		oldList = [...this.noticeList],
			// 		newList = [];
			// 	// TODO
			// 	this.$http('/api/common/bulletin', 'POST', {
			// 			shop_id: _that.$store.state.shopId,
			// 			page: page.num,
			// 			pageSize: 10
			// 		})
			// 		.then(data => {
			// 			if (page.num == 1) {
			// 				oldList = []
			// 			}
			// 			this.total = data.data.data.total;
			// 			newList = oldList.concat(data.data.data.data);
			// 			_that.mescroll.endBySize(10, data.data.data.total);
			// 		})

			// 	setTimeout(() => {
			// 		this.mescroll.endErr()
			// 	}, 1500)
			// },

		}
	}
</script>

<style lang="scss" scoped>
	.container {
		height: calc(100vh - var(--window-top));
		background-color: #F8F8F8;
	}

	.msg-item {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 24rpx;
		border-radius: 12rpx;
		padding: 24rpx;
		box-sizing: border-box;
		display: flex;
		background-color: white;

		.icon-box {
			width: 40rpx;
			height: 100%;
			margin-right: 10rpx;

			image {
				width: 40rpx;
				height: 40rpx;
			}
		}

		.detail {
			.head-box {
				display: flex;
				align-items: center;
				justify-content: space-between;

				.title {
					font-size: 30upx;
					font-weight: bold;
					color: #333333;
				}

				.date {
					font-size: 24upx;
					color: #777777;
				}
			}

			.main {
				margin-top: 22rpx;
				font-size: 28upx;
				color: #333333;
			}
		}
	}
</style>
