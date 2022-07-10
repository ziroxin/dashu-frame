<template>
	<view class="good-detail">
		<scroll-view scroll-y class="right-box" v-if="current==index">
			<view class="page-view">
				<view class="class-item">
					<view class="item-title" v-if="item">
						<text>{{item.name}}</text>
					</view>
					<view class="item-container">
						<block v-if="item">
							<view class="thumb-box">

								<image class="item-menu-image u-skeleton-rect" :src="item.img" mode="widthFix">
								</image>
								<view class="item-menu-box">
									<view class="item-menu-name u-skeleton-rect">{{item.title}}</view>
									<view class="item-menu-dosing u-skeleton-rect">主要原料：{{item.describe}}
									</view>
									<view class="item-menu-sales u-skeleton-rect">月售{{item.sales}}</view>
									<view class="item-menu-price-box">
										<view class="item-menu-price u-skeleton-rect">
											<view class="">
												<text class="unit">¥</text><text
													class="price">{{item.price_selling}}</text>
											</view>
											<view class="number-box">
												<!-- v-show="item['quantity']>0" -->
												<view class="minus-btn ufunc-btn">
													<image src="@/static/images/minus.png" mode="widthFix"
														@click="numberChange('minus',item)">
													</image>
												</view>
												<view class="number">
													{{items}}
												</view>
												<view class="add ufunc-btn ">
													<image src="@/static/images/add.png" mode="widthFix"
														@click="numberChange('add',item)">
													</image>
												</view>
											</view>
										</view>
									</view>
								</view>
							</view>
						</block>
						<block v-else>
							<view class="not-data" style="width:566rpx">
								<view class="img-box">
									<image src="@/static/images/no-data.png" mode="widthFix"></image>
								</view>
								<text class="not-text">暂无数据</text>
							</view>
						</block>
					</view>
				</view>
			</view>
		</scroll-view>

	</view>
</template>

<script>
	export default {
		name: "good-detail",
		props: {
			item: {
				type: Object,
			},
			current: Number,
			index: Number
		},
		data() {
			return {
				number: this.item.quantity
			};
		},
		computed: {
			items(){
				return this.item.quantity
			}
			// item: {
			// 	handler: (item, index) => {
			// 		this.number = index.quantity
			// 	},
			// 	deep: true
			// },
		},
		methods: {
			// 点餐操作 
			numberChange(type, item) {
				if (type == 'add') {
					this.$store.dispatch('updateShopDetails', {
						status: true,
						item: item
					})
				} else {
					this.$store.dispatch('updateShopDetails', {
						status: false,
						item: item
					})
				}
				console.log(item)
			},
		}
	}
</script>

<style lang="scss" scoped>
	.thumb-box {
		display: flex;
		align-items: center;
		margin-top: 20rpx;
	}

	.right-box {
		background-color: rgb(255, 255, 255);
	}

	.page-view {
		padding: 16rpx;
	}

	.class-item {
		margin-bottom: 30rpx;
		background-color: #fff;
		padding: 16rpx;
		border-radius: 8rpx;
	}

	.item-title {
		font-size: 26rpx;
		color: $u-main-color;
		font-weight: bold;
	}

	.item-menu-name {
		font-weight: normal;
		font-size: 24rpx;
		color: $u-main-color;
	}

	.item-menu-image {
		width: 186rpx !important;
		height: 186rpx !important;
		margin-right: 16rpx;

	}

	.item-menu-box {
		view {
			margin-top: 10rpx;

			&:frist-child {
				margin-top: 0;
			}
		}

		.item-menu-name {
			font-size: 28upx;
			font-weight: bold;
		}

		.item-menu-dosing {
			width: 312rpx;
			font-size: 24upx;
			color: #777777;
			display: block;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}

		.item-menu-sales {
			font-size: 22upx;
			color: #AAAAAA;
		}

		.item-menu-price {
			color: #FF7250;
			font-weight: bold;
			display: flex;
			align-items: center;
			justify-content: space-between;

			.unit {
				font-size: 26upx;
			}

			.price {
				font-size: 32upx;
			}

			.number-box {
				display: flex;
				justify-content: flex-end;
				align-items: center;
				margin-top: 0;

				.ufunc-btn {
					width: 44rpx;
					height: 44rpx;

					image {
						width: 44rpx;
						height: 44rpx;

					}
				}

				.number {
					height: 44rpx;
					line-height: 50rpx;
					margin: 0 30rpx;
				}
			}
		}
	}

	.not-data {
		width: 100rpx;
		text-align: center;

		.img-box {
			width: 294rpx;
			height: 294rpx;
			margin: 0 auto;
			margin-bottom: 20rpx;

			image {
				width: 294rpx;
				height: 294rpx;
			}
		}

		.not-text {
			font-size: 26upx;
			color: #333333;
		}
	}
</style>
