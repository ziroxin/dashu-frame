<template>
	<!-- 在线客服 -->
	<view class="container">
		<!-- #ifdef MP-ALIPAY -->
		<web-view class="online-service" src="https://kefu1.brt999.com/addons/kefu/index/mobile">
		</web-view>
		<!-- #endif -->
		<!-- #ifdef MP-WEIXIN -->
		<view class="common-problem">
			<rich-text :nodes="content"></rich-text>
		</view>
		<button class="service-btn" ref="serviceBtn" open-type="contact" @contact="handleContact">联系客服</button>
		<!-- #endif -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				content: ''
			}
		},
		onLoad() {

		},
		onShow() {
			this.$http('/api/common/problem','GET').then(res=>{
				this.content = res.data.data[0].content;
			})
		},
		onReady() {
			this.$nextTick(function(){
				console.log(this.$refs)
			})
		},
		methods: {
			handleContact(e) {
				console.log(e.detail.path)
				console.log(e.detail.query)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 750rpx;
		height: 750rpx;

		.online-service {
			padding-top: 44px;
		}
		
		.common-problem{
			padding: 24rpx;
		}
	}

	.service-btn {
		width: 694rpx;
		margin: 40rpx auto;
		color: white;
		background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
	}
</style>
