// h5端 引入复制插件
import '@/components/ican-clipBoard/ican-clipBoard.js'

let language = uni.getStorageSync('locale') || 'zh-CN';
let copySuccess, downloadError, saveSuccessText, saveErrorText;
if (language == 'zh-CN') {
	copySuccess = '复制成功';
	downloadError = '下载失败，请稍后重试';
	saveSuccessText = '保存成功';
	saveErrorText = '保存失败';
} else if (language == 'zh-TW') {
	copySuccess = '複製成功';
	downloadError = '下載失敗，請稍後重試';
	saveSuccessText = '保存成功';
	saveErrorText = '保存失敗';
} else {
	copySuccess = 'ok';
	downloadError = 'The download failed. Please try again later';
	saveSuccessText = 'save successfully';
	saveErrorText = 'fail to save';
}

// 兼容 h5 复制
export function copy(content) {
	uni.setClipboardData({
		data: content,
		success: function() {
			console.log(1)
			message(copySuccess)
		}
	})
}

// 修改tabBar栏内容语言
export function tabBarChange() {
	this.$nextTick(function() {
		let List = [],
			Obj = this._i18n.messages[language].tabBar;

		for (let key in Obj) {
			List.push(key)
		}
		List.map((item, index) => {
			uni.setTabBarItem({
				index: index,
				text: this.$t('tabBar.' + item)
			})
		})
	})
};
// 修改tabBar栏样式
export function tabBarChangeStyle() {
	this.$nextTick(function() {
		let List = [],
			theme = uni.getStorageSync('theme') || 'dark',
			Obj = this._i18n.messages[language].tabBar,
			color = theme == 'dark' ? '#C3C4CB' : '#777777',
			backgroundColor = theme == 'dark' ? '#1C2A53' : '#F8F8F8';
		// #ifdef H5
		window.document.documentElement.setAttribute('data-theme', theme);
		// #endif
		// #ifdef APP-PLUS
		plus.navigator.setStatusBarStyle(theme)
		// #endif
		for (let key in Obj) {
			List.push(key)
		}
		List.map((item, index) => {
			uni.setTabBarItem({
				index: index,
				iconPath: `static/images/${theme}/tabbar/${item}.png`,
				selectedIconPath: `static/images/${theme}/tabbar/${item}-active.png`
			})
		})
		uni.setTabBarStyle({
			color: color,
			backgroundColor: backgroundColor
		})
	})
};

// 提示框
export function message(content, direction = "center") {
	uni.showToast({
		title: content,
		icon: 'none',
		position: direction
	})
};

/**
 * 
 * @func JumpPage
 * @description 控制页面跳转
 * @param {String/Number} url (String)页面跳转的既定地址 / (Number)回退n个页面 
 * @param {String/Object} params 跳转所带参数
 * @param {Number} isTab  表明跳转类型 (1)跳转到Tab页面 / (2)重定向到指定页面 / (null)正常跳转到	指定页面
 * 
 * */
export function JumpPage(url, params, isTab) {
	if (typeof url === 'number') {
		uni.navigateBack({
			delta: url,
			animationType: 'pop-out',
			animationDuration: 300
		})
	}
	// 是否跳转到tab页
	if (isTab) {
		if (isTab == 1) {
			uni.switchTab({
				url: url
			})
		} else if (isTab == 2) {
			// 重定向
			uni.reLaunch({
				url: url
			})
		}
	} else {
		if (params) {
			let strParams = '';
			params = typeof params === 'object' ? JSON.parse(JSON.stringify(params)) : params;
			if (typeof params === 'object') {
				for (let s in params) {
					strParams += '' + s + '=' + params[s] + '&'
				}
				strParams = strParams.slice(0, -1)
				url += '?' + strParams
			} else {
				if (params.indexOf('=') != -1) {
					url += '?' + params
				}
			}
		}
		uni.navigateTo({
			url: url
		})
	}

}

export function test() {
	console.log('测试按钮')
}


/**
 * 
 * @func uploadImg
 * @description 从相册选择图片上传
 * @param {String} up_path 上传api地址
 * @param {Number} count 上传图片数量 默认为1  
 * @returns {object} data 返回值为一个对象
 * 
 * */
export function uploadImg(up_path, count = 1) {
	let _that = this;
	return new Promise((resolve, reject) => {
		uni.chooseImage({
			count: count,
			sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
			sourceType: ['album'], //从相册选择
			success: res => {
				let imgPaths = res.tempFilePaths;
				imgPaths.forEach((item, index) => {
					uni.uploadFile({
						url: this.$baseURL + up_path,
						filePath: item,
						// #ifdef MP-ALIPAY
						fileType: 'image',
						// #endif
						name: 'file',
						success: (res) => {
							if (res.statusCode == 200) {
								let data = JSON.parse(res.data);
								if (data.code == 1) {
									resolve(data);
								} else {
									_that.$message(data.msg)
								}
							}
						}
					})
				})
			}
		});
	})
}

/**
 * 
 * @func downloadToLocal
 * @todo 函数本地测试会出现跨域问题
 * @description 下载文件到本地
 * @param {type}  uri 文件的资源路径
 * @return {object} res 返回一个对象
 * 
 */

function downloadToLocal(uri) {
	uri = uri.slice(0, 1) != '/' ? uri : this.$baseURL + uri;
	//  H5有跨域问题 
	return new Promise((resolve, reject) => {
		uni.downloadFile({
			url: uri,
			success: (res) => {
				if (res.statusCode == 200) {
					resolve(res)
				}
			},
			fail() {
				message(downloadError)
			}
		})
	})

}

/**
 * 
 * @func saveImg
 * @description 保存图片到本地
 * @param {String}  uri 图片资源路径
 * @param {String}  fileName 保存的图片名称(仅H5支持)
 * 
 */
export function saveImg(uri, fileName = '') {
	downloadToLocal(uri)
		.then(data => {
			// #ifdef H5
			var oA = document.createElement('a');
			oA.download = fileName;
			oA.href = data.tempFilePath;
			document.body.appendChild(oA);
			oA.click();
			message(saveSuccessText);
			oA.remove(); // 下载之后把创建的元素删除
			// #endif
			// #ifndef H5
			uni.saveImageToPhotosAlbum({
				filePath: data.tempFilePath,
				success: (res) => {
					message(saveSuccessText);
				},
				fail: () => {
					message(saveErrorText);
				}
			})
			// #endif
		})
}

// 根据订单状态 判断订单文本展示
export function orderStatusText(payStatus) {
	// 付款状态，确认操作按钮，取消操作按钮
	let payStatusText, confirmText, cancelText;
	if (typeof payStatus !== 'string') {
		payStatus += ''
	}
	switch (payStatus) {
		case '-1':
			// 退款中
			payStatusText = '退款中';
			confirmText = '';
			cancelText = '取消退款';
			break;
		case '-2':
			// 已退款
			payStatusText = '已退款';
			confirmText = '';
			cancelText = '取消退款';
			break;
		case '-3':
			// 已取消
			payStatusText = '已取消';
			confirmText = '';
			cancelText = '删除订单';
			break;
		case '0':
			// 待支付
			payStatusText = '待支付';
			confirmText = '去支付';
			cancelText = '取消订单';
			break;
		case '1':
			// 已支付
			payStatusText = '已支付';
			confirmText = '';
			cancelText = '申请退款';
			break;
		case '2':
			// 已完成
			payStatusText = '已完成';
			confirmText = '';
			cancelText = '删除订单';
			break;
		default:
			// 待付款
			payStatusText = '待支付';
			confirmText = '去支付';
			cancelText = '取消订单';
			break;
	}
	return [payStatusText, confirmText, cancelText]
}
