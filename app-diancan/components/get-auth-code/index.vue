<template>
	<view class="getCode" :style="{
		color
	}">
		<text v-if="isShow" @click="getCode">{{codeText}}</text>
		<text v-else>{{time}}s</text>
	</view>
</template>

<script>
	export default {
		name: 'getAuthCode',
		props: {
			// 账户
			account: {
				type: [Number, String],
				required: true
			},
			// 验证码类型
			codeType: {
				type: String,
				default: 'tel'
			},
			// 参数
			params: {
				type: Object,
				default: function() {
					return {
						event: 'mobile'
					}
				}
			},
			// 倒计时时间
			downTime: {
				type: [Number, String],
				default: 60
			},
			// 文本内容
			text: {
				type: String,
				default: '获取验证码'
			},
			// 文本颜色
			color: String,
			// error文字
			errorText: {
				type: String,
				default: '账户不能为空'
			},
		},
		data() {
			return {
				// 倒计时
				time: this.downTime,
				// 计时器文本
				codeText: this.text,
				isShow: true,
			};
		},
		methods: {
			// 获取验证码
			getCode() {
				let _that = this;
				if (!_that.account) {
					_that.$message(_that.errorText);
					return false;
				};
				if (_that.codeType == 'tel') {
					// 获取手机验证码
					_that.$http('/api/send', 'POST', this.params)
						.then(data => {
							this.countDown();
							this.$message(data.data.msg)
						})
				} else {
					// 获取邮箱验证码
					_that.$http('/api/sendEmail', 'POST', this.params)
						.then(data => {
							this.countDown();
							this.$message(data.data.msg)
						})
				}

				// if (this.account == 'disable') {
				// 	// 登录状态下获取验证码
				// 	this.$http('users/getSmsCode', 'POST', this.params)
				// 		.then(data => {
				// 			this.countDown();
				// 			this.$message(data.data.message)
				// 		})
				// } else {
				// 	this.$http('index/sendSms', 'POST', this.params)
				// 		.then(data => {
				// 			this.countDown();
				// 			this.$message(data.data.message)
				// 		})
				// }
				// this.countDown()

			},
			// 倒计时
			countDown() {
				let _that = this,
					timer = null,
					originTime = _that.time;
				_that.isShow = !_that.isShow;
				_that.time = isNaN(_that.time) ? 60 : Math.floor(_that.time);
				if (timer == null) {
					timer = setInterval(() => {
						--_that.time
						if (_that.time == 0) {
							_that.isShow = !_that.isShow;
							clearInterval(timer)
							_that.time = originTime;
							_that.codeText = '重新获取'
						}
					}, 1000)
				}
			},

		}

	}
</script>

<style lang="scss">
	.getCode {
		white-space: nowrap;
		padding: 0 16rpx;
	}
</style>
