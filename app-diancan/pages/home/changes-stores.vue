<template>
	<!-- 切换门店 -->
	<view class="container u-skeleton">


		<mescroll-body class="mescroll-box" ref="mescrollRef" @down="downCallback" @up="upCallback">
			<block v-if="storeList.length>0">
				<view class="item">

					
				</view>
			</block>
			<block v-else>
				<view class="not-data" style="width:566rpx;margin-top: 40rpx;">
					<view class="img-box">
						<image src="@/static/images/no-data.png" mode="widthFix"></image>
					</view>
					<text class="not-text">暂无数据</text>
				</view>
			</block>
		</mescroll-body>
		<u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
	</view>
</template>

<script>
	import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
	export default {
		mixins: [MescrollMixin],
		data() {
			return {
				loading: true,
				value: '',
				operating: true, //营业状态
				storeList: []
			}
		},
		onLoad(options) {

		},
		onShow() {
			this.getStoreList();
		},
		methods: {
			getStoreList() {
				
			},
			switchStore(item) {
	
			},
			// downCallback(mescroll) {
			// 	this.$nextTick(function() {
			// 		this.mescroll.resetUpScroll();
			// 	})
			// },
			// upCallback(page) {
			// 	let _that = this,
			// 		oldList = [..._that.storeList];
			// 	this.$http('/api/index/shop_list', 'POST', {
			// 			merchant_id: this.$store.state.merchantId,
			// 			shop_id: this.$store.state.shopId,
			// 			page: page.num,
			// 			pageSize: 10
			// 		}, {
			// 			site: this.$store.state.latitudeAndLongitude || ""
			// 		})
			// 		.then(data => {
			// 			if (page.num == 1) {
			// 				oldList = []
			// 			};
			// 			console.log(data.data.data.shop_list)
			// 			_that.storeList = oldList.concat(data.data.data.shop_list);
			// 			_that.mescroll.endBySize(10, data.data.data.total);
			// 		})
			// },
			downCallback(mescroll) {
				mescroll.resetUpScroll();
				setTimeout(() => {
					this.mescroll.endErr();
					this.$nextTick(function() {
						this.loading = false;
					})
				}, 500)
			},
			upCallback() {
				console.log(1)
				setTimeout(() => {
					this.mescroll.endErr()
				}, 1500)
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		padding-top: 24rpx;
	}

	.u-search-box {
		width: 700rpx;
		height: 60rpx;
		margin: 0 auto;
		background-color: #F4F4F4;
		border-radius: 30rpx;
		padding-left: 28rpx;
		box-sizing: border-box;

		.u-search-inner {
			width: 100%;
			height: 100%;
			display: flex;
			justify-content: space-between;
			align-items: center;

			.input-container {
				height: 60rpx;
				display: flex;
				align-items: center;

				image {
					width: 30rpx;
					height: 30rpx;
					margin-right: 10rpx;
				}

				.search-input {
					font-size: 26upx;
				}
			}

			.btn {
				width: 124rpx;
				height: 60rpx;
				line-height: 60rpx;
				text-align: center;
				font-size: 28upx;
				color: white;
				background-color: #FF7250;
				border-radius: 30rpx;
			}

		}
	}

	.mescroll-box {}

	.item {
		width: 700rpx;
		height: 162rpx;
		margin: 0 auto;
		padding: 20rpx;
		box-sizing: border-box;
		background-color: #FFFFFF;
		display: flex;
		align-items: center;
		border-bottom: 1px solid #EEEEEE;

		.image-box {
			width: 106rpx;
			height: 106rpx;
			overflow: hidden;
			border-radius: 6rpx;
			position: relative;
			flex-shrink: 0;
			margin-right: 16rpx;

			image {
				width: 106rpx;
				height: 106rpx;

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
			width: calc(100% - 106rpx);

			view {
				display: flex;
				justify-content: space-between;
			}

			.title {

				.store-name {
					font-size: 30upx;
					font-weight: bold;
					color: #333333;
				}

				.change-btn {
					display: flex;
					align-items: center;
					color: #777777;

					.active {
						color: #FF7250;
					}

				}
			}

			.address-box {
				overflow: hidden;
				font-size: 24upx;
				color: #777777;
				margin-top: 30rpx;

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
	}
</style>
