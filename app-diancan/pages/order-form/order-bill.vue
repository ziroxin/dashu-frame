<template>
	<view class="container u-skeleton">
		<view>
			<view class="gongge-content uni-flex uni-row"
				style="-webkit-justify-content: center;justify-content: center;">
				<view class="item" v-for="(item,index) in gridList" :key="index" @click="uploadImage(item)">
					<view class="item-icon">
						<image class="u-skeleton-fillet" :src="item.icon" mode="widthFix"></image>
					</view>
					<view class="item-text">
						<text class="u-skeleton-fillet">{{item.text}}</text>
					</view>
				</view>
				<view class="item" @click="copyText(temp)">
					<view class="item-icon">
						<image class="u-skeleton-fillet" :src="textCopy.icon" mode="widthFix"></image>
					</view>
					<view class="item-text">
						<text class="u-skeleton-fillet">{{textCopy.text}}</text>
					</view>
				</view>
			</view>

			<view class="vvv">
				<text v-for="(item,index) in content" :key="index">{{item}}</text>
			</view>
		</view>
		<view class="example">
			<!-- 自定义表单校验 -->
			<uni-forms ref="orderBillForm" :modelValue="temp" validate-trigger="bind">
				<uni-forms-item label="单位名称" name="companyName">
					<uni-easyinput v-model="temp.companyName" placeholder="请输入单位名称" />
				</uni-forms-item>

				<uni-forms-item label="纳税人识别号" name="taxId">
					<uni-easyinput v-model="temp.taxId" placeholder="请输入纳税人识别号" />
				</uni-forms-item>

				<uni-forms-item label="单位地址" name="companyAdress">
					<uni-easyinput v-model="temp.companyAdress" placeholder="请输入单位地址" />
				</uni-forms-item>

				<uni-forms-item label="单位联系电话" name="companyPhone">
					<uni-easyinput v-model="temp.companyPhone" placeholder="请输入单位联系电话" />
				</uni-forms-item>

				<uni-forms-item label="开户行" name="bank">
					<uni-easyinput v-model="temp.bank" placeholder="请输入开户行" />
				</uni-forms-item>

				<uni-forms-item label="银行账号" name="bankAccount">
					<uni-easyinput v-model="temp.bankAccount" placeholder="请输入银行账号" />
				</uni-forms-item>

				<uni-forms-item label="开票金额" name="invoiceAmount">
					<uni-easyinput :styles="{color:'#333'}" v-model="temp.invoiceAmount" disabled
						placeholder="请输入开票金额" />
				</uni-forms-item>
			</uni-forms>
			<button type="primary" @click="submitBill('orderBillForm')">提交</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				temp: {
					orderId: '',
					companyName: '',
					taxId: '',
					companyAdress: '',
					companyPhone: '',
					bank: '',
					bankAccount: '',
					invoiceAmount: ''
				},
				// 校验规则
				billRules: {},
				state: 'create',
				src: '',
				content: [],
				gridList: [{
						type: 'upload',
						icon: require('@/static/images/order-form/icon-shangchuan.png'), //icon图
						text: '上传照片', //文本
					},
					{
						type: 'Photograph',
						icon: require('@/static/images/order-form/icon-paizhao.png'), //icon图
						text: '拍照上传', //文本
					}
				],
				textCopy: {
					type: 'copy',
					icon: require('@/static/images/order-form/icon-fuzhi.png'), //icon图
					text: '复制文本', //文本
				},
				invoiceAmountCopy: ''
			}
		},
		onReady() {
			this.getBillData()
			this.setRules()
			// 需要在onReady中设置规则
			this.$refs.orderBillForm.setRules(this.billRules)
		},
		onLoad(option) {
			const orderDetailList = JSON.parse(decodeURIComponent(option.orderDetailList));
			this.temp.invoiceAmount = orderDetailList.diningAmount
			this.invoiceAmountCopy = orderDetailList.diningAmount
			this.temp.orderId = orderDetailList.orderId
		},
		methods: {
			getBillData() {
				this.$http('/can/api/open/orderBill/ById', 'GET', {
					orderId: this.temp.orderId
				}).then(data => {
					const value = data.data.data
					if (value !== null) {
						this.temp = Object.assign({}, value)
						this.state = 'update'
					}
				})
			},
			// 设置校验规则
			setRules() {
				this.billRules = {
					companyName: {
						rules: [{
							required: true,
							errorMessage: '单位名称不能为空',
						}]
					},
					taxId: {
						rules: [{
								min: 15,
								max: 20,
								errorMessage: '长度在 15 到 20个字符之间'
							},
							{
								validateFunction: this.taxIdVerify
							}
						]
					},
					companyPhone: {
						rules: [{
							validateFunction: this.companyPhoneVerify
						}]
					},
					bankAccount: {
						rules: [{
							validateFunction: this.bankAccountVerify
						}]
					}
				}
			},
			submitBill() {
				if (this.state === 'update') {
					this.$refs.orderBillForm
						.validate()
						.then(() => {
							this.$http('/can/api/open/orderBill/update', 'POST', this.temp).then(data => {
								console.log(data.data);
								uni.showToast({
									title: '修改成功！',
									duration: 2000,
									icon: 'success',
								})
							})
						})
						.catch((err) => {
							console.log('表单校验失败：', err)
						})
				} else {
					this.$refs.orderBillForm
						.validate()
						.then(() => {
							this.$http('/can/api/open/orderBill/add', 'POST', this.temp).then(data => {
								console.log(data.data);
								uni.showToast({
									title: '添加成功！',
									duration: 2000,
									icon: 'success',
								})
							})
						})
						.catch((err) => {
							console.log('表单校验失败：', err)
						})
				}
			},
			taxIdVerify(rule, value, data, callback) {
				let taxIdVerify = /[0-9a-zA-Z]{1,6}/;
				if (!taxIdVerify.test(value)) {
					return callback('纳税人识别号只可以输入数字和字母')
				}
				callback()
			},
			companyPhoneVerify(rule, value, data, callback) {
				let companyPhoneVerify = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$|^0\d{2,3}-?\d{7,8}$/;
				if (!companyPhoneVerify.test(value)) {
					return callback('手机号或固定电话格式不正确')
				}
				callback()
			},
			bankAccountVerify(rule, value, data, callback) {
				let bankAccountVerify = /^[+]?(0|([1-9]\d*))?$/;
				if (!bankAccountVerify.test(value)) {
					return callback('请输入正确数字')
				}
				callback()
			},
			uploadImage(item) { //   选取照片，进行OCR识别
				if (item.type === 'upload') {
					uni.chooseImage({
						count: 1, //默认9
						sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
						success: (res) => {
							uni.showLoading({
								title: '正在识别中...'
							});
							this.src = res.tempFilePaths[0]; //后面还能用到 src 暂且留着
							// 下面进行转码
							wx.getFileSystemManager().readFile({
								filePath: res.tempFilePaths[0], //选择图片返回的相对路径
								encoding: 'base64', //编码格式
								success: (res) => { //成功回调页面

									// 数据拆分
									this.$http('/can/api/open/orderBill/textSplit', 'POST', {
										image: res.data
									}).then(data => {
										this.content = data.data.data.textData
										uni.hideLoading(); //把正在加载中隐藏
										console.log(this.content);
										this.temp = Object.assign({}, data.data.data);
										this.temp.invoiceAmount = this.invoiceAmountCopy
									}).catch((err) => {
										console.log(err, "errorziro");
									})
								}
							});
						}
					})
				} else{
					uni.chooseImage({
						count: 1, //默认9
						sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
						sourceType: ['camera', 'album'],
						success: (res) => {
							uni.showLoading({
								title: '正在识别中...'
							});
							this.src = res.tempFilePaths[0]; //后面还能用到 src 暂且留着
							// 下面进行转码
							wx.getFileSystemManager().readFile({
								filePath: res.tempFilePaths[0], //选择图片返回的相对路径
								encoding: 'base64', //编码格式
								success: (res) => { //成功回调页面
									// 数据拆分
									this.$http('/can/api/open/orderBill/textSplit', 'POST', {
										image: res.data
									}).then(data => {
										this.content = data.data.data.textData
										uni.hideLoading(); //把正在加载中隐藏
										console.log(this.content);
										this.temp = Object.assign({}, data.data.data);
										this.temp.invoiceAmount = this.invoiceAmountCopy
									}).catch((err) => {
										console.log(err, "errorziro");
									})
								}
							});
						}
					})
				}
			},
			copyText(data) {
				const value = data.companyName + "\n" + data.taxId + "\n" + data.companyAdress + "\n" + data.companyPhone +
					"\n" + data.bank + "\n" + data.bankAccount + "\n" + data.invoiceAmount + "元"
				uni.setClipboardData({
					data:value,
					success: function() {
						console.log('success');
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.example {
		padding: 15px;
		background-color: #fff;
	}

	.btn {
		width: auto;
		height: auto;
		margin-top: 30rpx;
		margin-left: 40%;
	}

	.title {
		padding: 5px 13px;
	}

	.uni-form-item__title {
		font-size: 26upx;
		line-height: 20px;
	}

	.uni-input-wrapper {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		padding: 8px 13px;
		flex-direction: row;
		flex-wrap: nowrap;
		background-color: whitesmoke;
	}

	.uni-input {
		height: 20px;
		line-height: 20px;
		font-size: 26upx;
		padding: 0px;
		flex: 1;
		background-color: whitesmoke;
	}

	.vvv {
		width: 99%;
		border: 2px dashed #eeeeee;
		height: 400upx;
		margin: 30upx 0 0 0;
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
</style>
