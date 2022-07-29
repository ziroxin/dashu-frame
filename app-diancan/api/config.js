// 接口服务地址
let baseURL = "https://www.keepgrown.com/server"
// 图片服务地址
let imgURL = ''
let testURL = ""
if (process.env.NODE_ENV === 'development') {
	// 开发环境
	//！！！！！！社区版只开放部分功能，商业版含全部功能，如需要请联系微信C500580
	//！！！！！！社区版只开放部分功能，商业版含全部功能，如需要请联系微信C500580
	//！！！！！！社区版只开放部分功能，商业版含全部功能，如需要请联系微信C500580
	baseURL = "http://localhost:8123"
	imgURL = 'http://localhost:8123'
} else {
	// 生产环境
	baseURL = "https://www.keepgrown.com/server"
	imgURL = 'https://www.keepgrown.com/server'
}

export {
	testURL,// 开发阶段本地模拟接口
	baseURL,// 接口API地址
	imgURL,// 接口图片API地址
};