<template>
	<!-- 收货地址 -->
	<view class="container">
		<view class="address-list">
			<view class="item" v-for="(item,index) in addressList" :key="index">
				<view class="header">
					<view class="user-info">
						<view class="name">
							王小二
						</view>
						<view class="phone-number">
							15111111111
						</view>
					</view>
					<view class="address-detail">
						<view class="icon-box" v-show="index == 0">
							默认
						</view>
						<view class="detailed-address">
							河南省郑州市高新技术开发区河南省郑州市高新技术开发区河南省郑州市高新技术开发区
						</view>
					</view>
				</view>
				<view class="footer">
					<view class="default-box" @click="setDefaultAddr(index)">
						<view class="choose-icon" :class="{'default':index == defailtAddr}">

						</view>
						<view class="default-text">
							设为默认
						</view>
					</view>
					<view class="handle-box">
						<view class="delete">
							删除
						</view>
						<view class="change" @click="JumpPage('/pages/my/add-addr',{type:'edit',addr:JSON.stringify({
				name: '王小二', //姓名
				phone: '15111111111', //电话
				region: '河南省郑州市高新技术开发区河南省郑州市高新技术开发区河南省郑州市高新技术开发区', //地区
				detailAddr: '河南省郑州市高新技术开发区河南省郑州市高新技术开发区河南省郑州市高新技术开发区', //详细地址
				remember: true,
			})})">
							修改
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="btn" @click="JumpPage('/pages/my/add-addr')">
			<image src="@/static/images/my/add.png" mode="widthFix"></image>
			添加地址
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				addressList: [{}, {}],
				defailtAddr: 0
			}
		},
		onLoad() {

		},
		onShow() {

		},
		methods: {
			setDefaultAddr(index) {
				this.defailtAddr = index;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		padding: 24rpx;
		min-height: calc(100vh - var(--window-top));
		box-sizing: border-box;
		background-color: #F8F8F8;
	}

	.address-list {

		.item {
			width: 700rpx;
			margin: 0 auto;
			border-radius: 12rpx;
			background-color: white;
			margin-bottom: 24rpx;
			padding: 24rpx;
			box-sizing: border-box;

			.header {
				border-bottom: 1px solid #EEEEEE;

				.user-info {
					display: flex;
					align-items: center;

					.name {
						font-size: 30upx;
						color: #333333;
					}

					.phone-number {
						font-size: 24upx;
						color: #777777;
						margin-left: 16rpx;
					}
				}

				.address-detail {
					display: flex;
					margin-top: 20rpx;
					padding-bottom: 20rpx;

					.icon-box {
						white-space: nowrap;
						min-width: 72rpx;
						height: 40rpx;
						border-radius: 8rpx;
						background-color: #FFE6DD;
						line-height: 40rpx;
						text-align: center;
						font-size: 22upx;
						color: #FF7250;
					}

					.detailed-address {
						margin-left: 16rpx;
						font-size: 28upx;
						color: #333333;
					}
				}
			}

			.footer {
				display: flex;
				align-items: center;
				justify-content: space-between;
				margin-top: 20rpx;

				view {
					display: flex;
					align-items: center;
				}

				.default-box {
					.choose-icon {
						width: 40rpx;
						height: 40rpx;
						border-radius: 50%;
						border: 1px solid #AAAAAA;

						&.default {
							@include bgImg('@/static/images/place-order/choose.png');
							border: none;

						}
					}

					.default-text {
						font-size: 26upx;
						color: #777777;
						margin-left: 16rpx;
					}
				}

				.handle-box {
					view {
						&:first-child {
							margin-right: 60rpx;
						}
					}
				}
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
		display: flex;
		align-items: center;
		justify-content: center;

		image {
			width: 40rpx;
			height: 40rpx;
			margin-right: 10rpx;
		}
	}
</style>
