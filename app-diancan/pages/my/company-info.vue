<template>
	<view class="container u-skeleton">
		<view class="company-data">
			<view class="item u-skeleton-fillet">
				<view class="label">
					门店名称
				</view>
				<view class="value">
					{{companyInfo.name}}
				</view>
			</view>
			<view class="item u-skeleton-fillet">
				<view class="label">
					所属行业
				</view>
				<view class="value">
					{{companyInfo.industry}}
				</view>
			</view>
			<view class="item u-skeleton-fillet">
				<view class="label">
					门店地址
				</view>
				<view class="value">
					{{companyInfo.address_info}}
					<image src="@/static/images/home/lead.png" mode="widthFix"></image>
				</view>
			</view>
			<view class="item u-skeleton-fillet">
				<view class="label">
					营业时间
				</view>
				<view class="value">
					{{companyInfo.open_time}}
				</view>
			</view>
			<view class="item u-skeleton-fillet">
				<view class="label">
					联系电话
				</view>
				<view class="value">
					{{companyInfo.mobile}}
					<image src="@/static/images/home/call.png" mode="widthFix"></image>
				</view>
			</view>
			<block v-if="Array.isArray(companyInfo.logo)">
				<view class="business-license" v-for="(item, index) in companyInfo.logo" :key="index">
					<u-lazy-load class="lazy-img-item" :image="item.logo"></u-lazy-load>
				</view>
			</block>

			<view class="business-license" v-else>
				<u-lazy-load class="lazy-img-item" :image="companyInfo.logo"></u-lazy-load>
			</view>

		</view>
		<u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				companyInfo: '',
				loading: false,
				// 营业执照
				licenseList: [{
					src: 'https://exp-picture.cdn.bcebos.com/b666b2530688912cb92f3f5b1b4800fc76f79778.jpg?x-bce-process=image%2Fresize%2Cm_lfit%2Cw_500%2Climit_1%2Fformat%2Cf_jpg%2Fquality%2Cq_80',
				}],
			}
		},
		onLoad() {

		},
		onShow() {
			this.getCompanyInfo()
		},
		methods: {
			getCompanyInfo() {
				let _that = this;
				_that.$http('/api/shop/shopInfo', 'POST', {
						shop_id: _that.$store.state.shopId
					})
					.then(data => {
						_that.companyInfo = data.data.data;
						setTimeout(() => {
							_that.$nextTick(function() {
								_that.loading = false;
							})
						}, 1000)
					})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		.company-data {
			width: 100%;
			padding: 0 24rpx;
			box-sizing: border-box;
			margin-top: 44rpx;

			.item {
				display: flex;
				justify-content: space-between;
				margin-bottom: 44rpx;

				&:last-child {
					margin-bottom: 0;
				}

				.label {
					font-size: 26upx;
					color: #777777;
				}

				.value {
					font-size: 28upx;
					font-weight: bold;
					color: #333333;
					display: flex;
					white-space: nowrap;

					image {
						width: 40rpx;
						height: 40rpx;
						margin-left: 10rpx;
					}
				}
			}

			.business-license {
				width: 100%;
				margin-bottom: 24rpx;

				.lazy-img-item {
					width: 100%;
					height: 100%;

				}
			}
		}
	}
</style>
