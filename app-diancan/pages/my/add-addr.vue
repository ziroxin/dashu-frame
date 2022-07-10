<template>
	<!-- 新增地址 -->
	<view class="container">
		<u-form class="u-form-box" :model="model" :rules="rules" ref="uForm">
			<u-form-item label-width="150" label="收货人：" prop="name">
				<u-input placeholder="请输入收货人姓名" v-model="model.name" type="text"></u-input>
			</u-form-item>
			<u-form-item label="手机号码：" prop="phone" label-width="150">
				<u-input placeholder="请输入收货人手机号" v-model="model.phone" type="number"></u-input>
			</u-form-item>
			<!-- #ifndef MP-ALIPAY -->
			<u-form-item label="地区信息：" prop="region" label-width="150">
				<u-input v-if="showBox" type="select" :select-open="pickerShow" v-model="model.region"
					placeholder="请选择地区" @click="openAddrBox" style="color: #333333;"></u-input>
			</u-form-item>
			<!-- #endif -->
			
			<!-- #ifdef MP-ALIPAY -->
			<u-form-item class="alipay-region-item" label="地区信息：" prop="region" label-width="150">
				<!-- 省 -->
				<u-input class="sheng area-info" v-if="showBox" type="select" :select-open="pickerShow"
					v-model="model.region.provincelist" placeholder="请选择省份" @click="openAddrProvinceBox"
					style="color: #333333;"></u-input>
				<!-- 市 -->
				<u-input class="shi area-info" v-if="showBox" type="select" :select-open="pickerShow"
					v-model="model.region.citylist" placeholder="请选择市" @click="openAddrCityBox" style="color: #333333;">
				</u-input>
				<!-- 区 -->
				<u-input class="qu area-info" v-if="showBox" type="select" :select-open="pickerShow"
					v-model="model.region.area" placeholder="请选择地区" @click="openAddrAreaBox" style="color: #333333;">
				</u-input>
			</u-form-item>
			<!-- #endif -->

			</u-form-item>
			<u-form-item label="详细地址：" prop="detailAddr" label-width="150">
				<u-input type="textarea" placeholder="如道路、门牌号、小区、楼栋号等" v-model="model.detailAddr" />
			</u-form-item>
			<!-- 此处switch的slot为right，如果不填写slot名，也即<u-switch v-model="model.remember"></u-switch>，将会左对齐 -->
			<u-form-item label="设为默认" prop="remember" label-width="150">
				<u-switch v-if="showBox" v-model="model.remember" slot="right" active-color="#FF7250"
					inactive-color="#FFFFFF"></u-switch>
			</u-form-item>
		</u-form>
		<view class="btn">
			保存
		</view>
		<!-- #ifdef MP-ALIPAY -->
		<view class="region-picker">
			<!-- 省 -->
			<u-picker mode="selector" safe-area-inset-bottom :range="['河南省']" v-model="pickerShowProvince"
				@confirm="regionConfirmProvince"></u-picker>
			<!-- 市 -->
			<u-picker mode="selector" safe-area-inset-bottom :range="['郑州市']" v-model="pickerShowCity"
				@confirm="regionConfirmCity"></u-picker>
			<!-- 区 -->
			<u-picker mode="selector" safe-area-inset-bottom :range="['老城区']" v-model="pickerShowArea"
				@confirm="regionConfirmArea"></u-picker>
		</view>
		<!-- #endif -->
		<!-- #ifndef MP-ALIPAY -->
		<u-picker mode="region" safe-area-inset-bottom v-model="pickerShow" @confirm="regionConfirm"></u-picker>
		<!-- #endif -->

	</view>
</template>

<script>
	export default {
		data() {
			return {
				showBox: false,
				pickerShow: false, //地区下拉选框展示
				// #ifdef MP-ALIPAY
				pickerShowProvince: false, //地区下拉选框展示 省
				pickerShowCity: false, //地区下拉选框展示 市
				pickerShowArea: false, //地区下拉选框展示 区
				// #endif
				model: {
					name: '', //姓名
					phone: '', //电话
					// #ifdef MP-ALIPAY
					region: {
						provincelist: '', //省
						citylist: '', //市
						area: '', //区
					}, //地区
					// #endif
					// #ifndef MP-ALIPAY
					region: '', //地区
					// #endif
					detailAddr: '', //详细地址
					remember: false,
				},
				rules: {
					name: [{
						required: true,
						message: '请输入姓名',
						trigger: 'blur',
					}],
					phone: [{
						required: true,
						message: '请输入电话',
						trigger: 'blur',
					}],
					region: [{
						required: true,
						message: '请选择地区',
						trigger: 'blur',
					}],
					detailAddr: [{
						required: true,
						message: '请输入详细地址',
						trigger: 'blur',
					}],

				}
			}
		},
		onLoad(options) {
			if (options.type == 'edit') {
				uni.setNavigationBarTitle({
					title: '编辑地址'
				})
			}
			if (options.addr) {
				// #ifndef MP-ALIPAY
				Object.assign(this.model, JSON.parse(options.addr))
				// #endif
			}
		},
		onShow() {

		},
		onReady() {
			this.showBox = !this.showBox;
		},
		methods: {
			// 打开地区下拉选框
			openAddrBox() {
				this.pickerShow = !this.pickerShow;
			},
			// 选择地区回调
			regionConfirm(e) {
				this.model.region = e.province.label + '-' + e.city.label + '-' + e.area.label;
			},
			// #ifdef MP-ALIPAY
			// 打开地区下拉选框 省
			openAddrProvinceBox() {
				this.pickerShowProvince = !this.pickerShowProvince
			},
			// 打开地区下拉选框 市
			openAddrCityBox() {
				this.pickerShowCity = !this.pickerShowCity
			},
			// 打开地区下拉选框 区
			openAddrAreaBox() {
				this.pickerShowArea = !this.pickerShowArea
			},
			// 选择地区回调 省
			regionConfirmProvince(e) {
				console.log(e)
				this.model.region.provincelist = '河南省' //省
			},
			// 选择地区回调 市
			regionConfirmCity(e) {
				console.log(e)
				this.model.region.citylist = '郑州市' //市
			},
			// 选择地区回调 区
			regionConfirmArea(e) {
				console.log(e)
				this.model.region.area = '二七区' //区
			},
			// #endif

		}
	}
</script>

<style lang="scss" scoped>
	.container {
		padding: 0 24rpx;
		box-sizing: border-box;
	}

	.region-picker {}

	.alipay-region-item {
		display: flex;

		/deep/ .area-info {
			display: inline-block;
			width: 33%;
		}
	}

	.u-form-box {
		width: 100%;
	}

	.btn {
		width: 700rpx;
		height: 88rpx;
		margin: 0 auto;
		line-height: 88rpx;
		text-align: center;
		font-size: 30upx;
		color: white;
		border-radius: 12rpx;
		background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
		margin-top: 100rpx;
	}
</style>
