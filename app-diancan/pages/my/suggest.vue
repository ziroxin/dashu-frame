<template>
	<!-- 改进建议 -->
	<view class="container">
		<view class="item">
			<view class="title">
				反馈建议
			</view>
			<view class="content opinion">
				<textarea v-model="opinion" placeholder="如有合理建议,请联系作者"
					:maxlength="opinionMaxLen" />
				<!-- #ifndef MP-ALIPAY -->
				<view class="counter">
					{{opinion.length}}/{{opinionMaxLen}}
				</view>
				<!-- #endif -->
			</view>
		</view>
		<view class="item">
			<view class="title">
				上传图片
			</view>
			<view class="content upload-box">
				<u-image width="220rpx" height="220rpx" :src="image"></u-image>
				<view class="upload-btn" @click="uploadImg">
					上传图片
				</view>
			</view>
		</view>
		<view class="item">
			<view class="title">
				联系电话
			</view>
			<view class="content">
				<input type="text" v-model="phoneNumber" placeholder="请输入您的联系电话" />
			</view>
		</view>

		<view class="item">
			<view class="btn" @click="submit">
				保存提交
			</view>
			<view class="tip">
				合理的改进建议被采纳后，会有精美礼品相送哦！
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				opinion: '', // 意见内容
				opinionMaxLen: '200',
				phoneNumber: '', //手机号
				image: ''
			}
		},
		onLoad() {

		},
		onShow() {

		},
		onReady() {},
		methods: {
			uploadImg() {
				this.$uploadImg('/api/common/uploadali', 1)
					.then(data => {
						this.image = data.data.fullurl
					})
			},
			submit() {
				let _that = this;
				_that.$http('/api/user/feedback', 'POST', {
						mobile: _that.phoneNumber,
						feedback: _that.opinion,
						img: _that.image,
						shop_id: _that.$store.state.shopId
					})
					.then(data => {
						_that.$message('感谢您的建议')
						setTimeout(() => {
							_that.JumpPage(1)
						}, 500)
					})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		padding: 24rpx;
		box-sizing: border-box;
	}

	.item {
		width: 700rpx;
		margin: 0 auto;
		margin-top: 46rpx;

		.title {
			color: #333333;
			font-size: 32upx;
			font-weight: bold;
			margin-bottom: 24rpx;
		}

		.content {
			textarea {
				width: 100%;
				height: 194rpx;
				border-bottom: 1px solid #EEEEEE;
				font-size: 28upx;
			}

			input {
				width: 100%;
				height: 60rpx;
				border-bottom: 1px solid #EEEEEE;
				font-size: 26upx;
			}

			&.opinion {
				position: relative;

				.counter {
					position: absolute;
					bottom: 26rpx;
					right: 0;
					color: #AAAAAA;
					font-size: 24upx;
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
			margin-top: 60rpx;
		}

		.tip {
			color: #F65D5D;
			font-size: 22upx;
			margin-top: 16rpx;
		}
	}

	.upload-btn {
		margin-top: 20rpx;
	}
</style>
