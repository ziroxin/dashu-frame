<template>
	<view class="container u-skeleton">
		<!-- #ifndef MP-ALIPAY -->
		<u-navbar class="navbar" title-color="white" title="我的" :background="backGround" :is-back="false"
			:isFixed="false">
		</u-navbar>
		<!-- #endif -->
		<!-- #ifdef MP-ALIPAY -->
		<u-navbar class="navbar" title-color="white" :background="backGround" :is-back="false" :isFixed="false">
		</u-navbar>
		<!-- #endif -->
		<view class="header-box">
			<view class="user-info">
				<view class="info-detail">

					<view class="info" v-if="!oauthInfo['loginCode']">
						<image class="header-img u-skeleton-circle" src="@/static/images/logo.png" mode="widthFix">
						</image>
						<view class="get-auth-box">
							<!-- #ifdef MP-WEIXIN -->
							<button class="get-auth-btn" type="default" @tap="getWXUserInfo">获取授权</button>
							<!-- #endif -->
							<!-- #ifndef MP-WEIXIN -->
							<button class="get-auth-btn" v-if="reGet" type="default" @tap="getAuth">获取授权</button>
							<!-- #endif -->
						</view>
					</view>
					<view class="info" v-else>
						<image v-if="info.avatar" class="header-img u-skeleton-circle" :src="info.avatar"
							mode="widthFix">
						</image>
						<image v-else class="header-img u-skeleton-circle" src="@/static/images/logo.png"
							mode="widthFix">
						</image>
						<view class="user-info-box">
							<view class="user-name u-skeleton-fillet">
								<text>{{info.username}}</text>
								<!-- <image class="refresh-btn" src="@/static/images/my/refresh.png" mode="widthFix"
									@click="refresh"></image> -->
							</view>
							<view class="user-phone u-skeleton-fillet">
								<button class="get-phone-btn " v-if="!info.mobile" type="default"
									open-type="getPhoneNumber" @getphonenumber="getPhoneNumber"
									scope='phoneNumber'>获取手机号</button>
								<view class="phone-number " v-else>
									{{info.mobile}}
								</view>
							</view>
						</view>
					</view>
				</view>
				<view class="handle" @click="JumpPage('/pages/my/msg-list')">
					<image src="@/static/images/my/icon-msg.png" mode="widthFix"></image>
				</view>
			</view>

			<view class="assets-box">
				<view class="price-box item u-skeleton-fillet">
					<view class="title">
						余额
					</view>
					<!--  @click="JumpPage('/pages/my/balance-withdraw')" -->
					<view class="value">
						<text class="value-text"> {{info.money||0}} </text><text class="unit-text">元</text>
					</view>
					<!-- #ifdef MP-ALIPAY -->
					<view class="handle-box" @click="JumpPage('/pages/my/balance-recharge')">
						充值
						<image src="@/static/images/my/right-arrow.png" mode="widthFix"></image>
					</view>
					<!-- #endif -->
					<!-- #ifdef MP-WEIXIN -->
					<!-- <view class="handle-box" @click="$message('暂未开放')">
						充值
						<image src="@/static/images/my/right-arrow.png" mode="widthFix"></image>
					</view> -->
					<!-- #endif -->
				</view>
				<view class="discounts-box item u-skeleton-fillet">
					<view class="title">
						优惠券
					</view>
					<view class="value">
						<text class="value-text"> {{couponLength}} </text><text class="unit-text">张</text>
					</view>
					<view class="handle-box" @click="JumpPage('/pages/my/discounts-card')">
						使用
						<image src="@/static/images/my/right-arrow.png" mode="widthFix"></image>
					</view>
				</view>
			</view>
		</view>

		<view class="gongge-box">
			<view class="title u-skeleton-fillet" @click="gogogo">
				<text>常用工具</text>
			</view>
			<view class="gongge-content">
				<view class="item" v-for="(item,index) in gridList" :key="index" @click="clickGGItem(item)">
					<view class="item-icon">
						<image class="u-skeleton-fillet" :src="item.icon" mode="widthFix"></image>
					</view>
					<view class="item-text">
						<text class="u-skeleton-fillet">{{item.text}}</text>
					</view>
				</view>
			</view>
		</view>
		<u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
		<u-modal v-model="clearCacheShow" title="清除缓存" content="是否清除缓存？" show-cancel-button
			@confirm="confirmClearCacheShow"></u-modal>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import {
		getPhoneNumber
	} from '@/common/oauth.js'
	export default {
		data() {
			return {
				loading: true,
				backGround: {
					background: 'linear-gradient(90deg, #FA8A43 0%, #FF7250 100%)',
				},
				gridList: [
					// {
					// 	path:'/pages/my/scan-recharge',
					// 	path: 'not', // 跳转路径
					// 	icon: require('@/static/images/my/icon-to-pay.png'), //icon图
					// 	text: '去支付', //文本
					// },
					{
						path: '/pages/my/balance-details', // 跳转路径
						icon: require('@/static/images/my/icon-yue.png'), //icon图
						text: '余额明细', //文本
					},
					{
						path: '/pages/my/coupon-code-center', // 跳转路径
						icon: require('@/static/images/my/icon-kabao.png'), //icon图
						text: '领券中心', //文本
					},

					{
						path: '/pages/my/company-info', // 跳转路径 TODO
						icon: require('@/static/images/my/icon-company-info.png'), //icon图
						text: '商家信息', //文本
					},					
					{
						path: '/pages/my/online-service', // 跳转路径
						icon: require('@/static/images/my/icon-kefu.png'), //icon图
						text: '在线客服', //文本
					},
					{
						path: '/pages/my/suggest', // 跳转路径
						icon: require('@/static/images/my/icon-jianyi.png'), //icon图
						text: '改进建议', //文本
					},
					{
						path: 'clear', // 跳转路径 TODO
						icon: require('@/static/images/my/icon-qinghuancun.png'), //icon图
						text: '清除缓存', //文本
					},
				],
				// 已登录
				isLogin: true,
				authCode: '',
				code: '',
				userphone: '',
				info: {
					username: '',
					mobile: '',
				},
				reGet: true,
				couponLength: 0,
				clearCacheShow: false,
				// #ifdef MP-WEIXIN
				wxLoginCode: '',
				// #endif
			}
		},
		computed: {
			...mapState(['oauthInfo'])
		},
		onLoad() {
			// #ifdef MP-WEIXIN
			this.getAuth();
			// #endif
		},
		onShow() {
			setTimeout(() => {
				this.$nextTick(function() {
					this.loading = false;
				})
			}, 500)

			this.$http('/api/coupon/user_coupon', 'POST')
				.then(data => {
					this.couponLength = data.data.data.length
				})
			this.getUserInfoDetail();
		},
		methods: {
			async getAuth(e) {
				let _that = this;
				let info = await this.$oauth(e);
				if (info.loginCode) {
					// #ifdef MP-ALIPAY
					_that.$http('/api/ali_user/ali_user', 'POST', {
							code: info.loginCode
						})
						.then(data => {
							_that.$store.commit('SET_TOKEN', data.data.data.userinfo.token);
							_that.getUserInfoDetail();
							_that.$store.commit('SET_OAUTH_INFO', info);
						}).catch((err) => {
							console.log(JSON.stringify(err))
						})
					// #endif
					// #ifdef MP-WEIXIN
					this.wxLoginCode = info.loginCode;
					// #endif
				}

			},
			getWXUserInfo() {
				let _that = this;
				if (uni.getUserProfile) {
					uni.getUserProfile({
						desc: '获取你的昵称、头像、地区及性别',
						lang: 'zh_CN',
						success: (res) => {
							if (res.userInfo) {
								_that.$http('/api/wx_login/wxlogin', 'POST', {
										code: _that.wxLoginCode,
										wx_name: res.userInfo.nickName,
										avatar: res.userInfo.avatarUrl,
										gender: res.userInfo.gender
									})
									.then(data => {
										_that.$store.commit('SET_TOKEN', data.data.data.userinfo.token);
										_that.getUserInfoDetail();
										_that.$store.commit('SET_OAUTH_INFO', {
											loginCode: _that.wxLoginCode
										});
									}).catch((err) => {
										console.log(JSON.stringify(err))
									})
							}
						},
						fail: (err) => {
							console.log(err)
							this.$message('用户取消授权')
						}
					})
				} else {
					console.log('wx')
					wx.getUserProfile({
						desc: '获取你的昵称、头像、地区及性别',
						lang: 'zh_CN',
						success: (res) => {
							if (res.userInfo) {
								_that.$http('/api/wx_login/wxlogin', 'POST', {
										code: _that.wxLoginCode,
										wx_name: res.userInfo.nickName,
										avatar: res.userInfo.avatarUrl,
										gender: res.userInfo.gender
									})
									.then(data => {
										_that.$store.commit('SET_TOKEN', data.data.data.userinfo.token);
										_that.getUserInfoDetail();
										_that.$store.commit('SET_OAUTH_INFO', {
											loginCode: _that.wxLoginCode
										});
									}).catch((err) => {
										console.log(JSON.stringify(err))
									})
							}
						},
						fail: (err) => {
							console.log(err)
							this.$message('用户取消授权')
						}
					})
				}

			},
			getUserInfoDetail() {
				let _that = this;
				_that.$http('/api/user/userInfo', 'POST')
					.then(data => {
						Object.assign(_that.info, data.data.data)
					})
			},
			// 获取手机号
			async getPhoneNumber(e) {
				let _that = this,
					obj = {},
					uri = '/api/ali_user/getPhone';
				// #ifdef MP-ALIPAY
				let phoneNumber = await getPhoneNumber();
				Object.assign(obj, {
					content: JSON.parse(phoneNumber).response
				})
				uri = '/api/ali_user/getPhone';
				// #endif
				// #ifdef MP-WEIXIN
				Object.assign(obj, {
					code: _that.wxLoginCode,
					encryptedData: e.detail.encryptedData,
					iv: e.detail.iv
				})
				console.log(obj)
				uri = "/api/wx_login/getPhoneNumber";
				// #endif
				this.$http(uri, 'POST', obj)
					.then(data => {
						_that.getUserInfoDetail();
					})
			},
			refresh() {
				this.isLogin = !this.isLogin;
			},
			clickGGItem(item) {
				if (this.info.nickName == '') {
					this.$message('未授权，请先授权登录');
					return false
				}
				if (item.path == 'clear') {
					this.clearCacheShow = !this.clearCacheShow;
				} else if (item.path == 'not') {
					this.$message('暂未开放')
				} else {
					this.JumpPage(item.path)
				}
			},
			confirmClearCacheShow() {
				this.$store.commit('SET_OAUTH_INFO', '')
				this.$forceUpdate();
				this.$message('已清除缓存')
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		min-height: calc(100vh - var(--window-bottom));
		background-color: white;
	}

	.header-box {
		width: 100%;
		height: calc(428rpx - 44px);
		padding-top: 20rpx;
		background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
	}

	.user-info {
		display: flex;
		align-items: center;
		justify-content: space-between;
		width: 100%;
		padding: 0 24rpx;
		box-sizing: border-box;

		.info-detail {
			// display: flex;
			// align-items: center;

			.header-img {
				width: 100rpx;
				height: 100rpx;
				border-radius: 50%;
				margin-right: 18rpx;
			}

			.info {
				display: flex;
				align-items: center;

				.user-name {
					display: flex;
					align-items: center;
					font-size: 28upx;
					color: white;
					white-space: nowrap;

					@keyframes rotatefresh {
						from {
							transform: rotate(0deg);
						}

						to {
							transform: rotate(360deg);
						}
					}

					image {
						margin-left: 20rpx;
						width: 32rpx;
						height: 32rpx;

						/* #ifndef MP-WEIXIN */
						&:active {
							animation: rotatefresh 1s;
						}

						/* #endif */
						/* #ifdef MP-WEIXIN */
						&:hover {
							animation: rotatefresh 1s;
						}

						/* #endif */

					}
				}

				.user-phone {
					margin-top: 12rpx;

					.get-phone-btn {
						min-width: 148rpx;
						height: 42rpx;
						font-size: 24upx;
						color: #FF7250;
						background-color: white;
						border-radius: 8rpx;
						text-align: center;
						line-height: 42rpx;
					}

					.phone-number {
						color: white;
						font-size: 26upx;
					}
				}

				.get-auth-box {
					.get-auth-btn {
						min-width: 148rpx;
						height: 56rpx;
						line-height: 56rpx;
						font-size: 28upx;
						color: #FF7250;
						background-color: white;
						border-radius: 8rpx;
						text-align: center;
						padding: 0 12rpx;
						box-sizing: border-box;
					}
				}
			}
		}

		.handle {
			margin-right: 20rpx;
			width: 50rpx;
			height: 50rpx;

			image {
				width: 50rpx;
				height: 50rpx;
			}
		}
	}

	.assets-box {
		width: 100%;
		padding: 0 24rpx;
		margin: 24rpx 0;
		box-sizing: border-box;
		display: flex;
		justify-content: space-between;
		align-items: center;
		color: white;

		.item {
			width: 50%;

			.title {
				margin-bottom: 10rpx;
				font-size: 26upx;
			}

			.value {
				margin-bottom: 12rpx;

				.value-text {
					font-size: 40upx;
					font-weight: bold;
				}

				.unit-text {
					font-size: 28upx;
					margin-left: 10rpx;
				}
			}

			.handle-box {
				image {
					width: 20rpx;
					height: 20rpx;
				}
			}
		}
	}

	.main {
		text-align: center;
		margin-top: 6rpx;

		.unit {
			font-size: 32upx;
		}

		.price {
			font-size: 40upx;
		}
	}

	.footer {
		margin-top: 16rpx;
		display: flex;
		justify-content: space-around;

		.btn {
			width: 230rpx;
			height: 64rpx;
			border-radius: 8rpx;
			border: 2px solid #FF7250;
			color: #FF7250;
			font-size: 28upx;
			text-align: center;
			line-height: 64rpx;
			box-sizing: border-box;

			&.recharge {
				background-color: #FF7250;
				color: white;
			}
		}

	}

	.gongge-box {
		width: 100%;
		padding: 0 24rpx;
		box-sizing: border-box;
		margin-top: 46rpx;

		.title {
			font-size: 32upx;
			font-weight: bold;
			color: #333333;
		}

		.gongge-content {
			display: flex;
			flex-flow: wrap;

			.item {
				width: 25%;
				text-align: center;
				margin-top: 24rpx;

				.item-icon {
					image {
						width: 84rpx;
						height: 84rpx;
					}
				}

				.item-text {
					font-size: 26upx;
					color: #333333;
					margin-top: 16rpx;
				}
			}
		}
	}
</style>
