<template>
	<!-- 邀请好友 -->
	<view class="container">
		<!-- #ifndef MP-ALIPAY -->
		<view class="back-btn" @click="JumpPage(1)">
			<u-icon name="arrow-left" color="white" size="44"></u-icon>
		</view>
		<!-- #endif -->
		<!-- #ifndef MP-WEIXIN -->
		<view class="share-box"
			:class="[{'invite-1':currentBgImg==1},{'invite-2':currentBgImg==2},{'invite-3':currentBgImg==3}]">
		</view>
		<!-- #endif -->
		<!-- #ifdef MP-WEIXIN -->
		<view class="share-box invite-1" v-if="currentBgImg==1"
			style="background-image: url('https://hnsoft.brt999.com/diancanbao/invite-bg-1.png');">
		</view>
		<view class="share-box invite-1" v-if="currentBgImg==2"
			style="background-image: url('https://hnsoft.brt999.com/diancanbao/invite-bg-2.png');">
		</view>
		<view class="share-box invite-1" v-if="currentBgImg==3"
			style="background-image: url('https://hnsoft.brt999.com/diancanbao/invite-bg-3.png');">
		</view>
		<!-- #endif -->
		<view class="user-invite-qrcode">
		
		</view>

		<scroll-view class="choose-bg-img" scroll-x="true">
			<view class="choose-box">
				<view class="img-box" v-for="(item,index) in previewList" :key="index"
					@click="chooseInviteBgImg(index)">
					<image :src="item.img" mode="widthFix"></image>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {

				backGround: {
					backgroundColor: 'tranparent',
				},
				previewList: [
				
				], //预选图列表
				// 当前背景
				currentBgImg: '1'
			}
		},
		onLoad() {

		},
		onShow() {

		},
		methods: {
			chooseInviteBgImg(index) {
				this.currentBgImg = (index + 1)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		// position: fixed;
		width: 100%;
		height: 100vh;
		top: 0;
	}

	.back-btn {
		width: 44rpx;
		height: 44rpx;
		position: fixed;
		top: calc(var(--status-bar-height) + 24rpx);
		z-index: 99;
		left: 24rpx;
	}

	.share-box {
		width: 100%;
		height: 100%;
		position: relative;

		@for $idx from 1 to 4 {
			&.invite-#{$idx} {
				@include bgImg('https://hnsoft.brt999.com/diancanbao/invite-bg-#{$idx}.png');
			}
		}
	}

	.user-invite-qrcode {
		position: absolute;
		bottom: 400rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 680rpx;
		height: 220rpx;
		padding: 24rpx;
		box-sizing: border-box;
		left: 0;
		right: 0;
		margin: 0 auto;
		border-radius: 12rpx;
		background: rgba(255, 255, 255, 0.75);

		&::after {
			width: 680rpx;
			height: 220rpx;
			top: 0;
			left: 0;
			content: ' ';
			position: absolute;
			// z-index: -1;
			filter: blur(10px);
		}

		.user-info-box {
			display: flex;
			align-items: center;

			.head-photo {
				width: 88rpx;
				height: 88rpx;
				border-radius: 50%;
				overflow: hidden;

				image {
					width: 88rpx;
					height: 88rpx;
				}
			}

			.user-info {
				margin-left: 16rpx;

				.username {
					font-size: 28upx;
					color: #333333;
					font-weight: bold;
				}

				.date {
					font-size: 26upx;
					color: #777777;
					margin-top: 12rpx;
				}
			}
		}

		.qrcode-box {
			width: 172rpx;
			height: 172rpx;

			image {
				width: 172rpx;
				height: 172rpx;

			}
		}
	}

	.choose-bg-img {
		width: 100%;
		height: 276rpx;
		background-color: white;
		position: absolute;
		bottom: 0;

		.choose-box {
			height: 100%;
			display: flex;
			align-items: center;
			justify-content: space-around;

			.img-box {
				width: 180rpx;
				height: 220rpx;

				image {
					width: 180rpx;
					height: 220rpx;
				}

			}
		}

	}
</style>
