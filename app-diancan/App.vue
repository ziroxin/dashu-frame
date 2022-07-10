<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "uview-ui/index.scss";
	.container{
		min-height: calc(100vh-var(--window-top));
		/* #ifdef MP-ALIPAY */
		input,textarea{
			background-color: transparent;
		}
		/* #endif */
	}
	.not-data {
		width: 100%;
		margin: 0 auto;
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

		text {
			font-size: 26upx;
			color: #333333;
			margin-top: 20rpx;
		}
	}

	text.unit {
		color: #FF7250;
	}

	text.price {
		font-weight: bold;
		color: #FF7250;
	}
</style>
