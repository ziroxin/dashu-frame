<template>
    <!-- 首页 -->
    <view class="container u-skeleton">
        <!-- #ifndef MP-ALIPAY -->
        <u-navbar class="navbar" title-color="white" title="首页" :background="backGround" :is-back="false"
            :isFixed="false">
        </u-navbar>
        <!-- #endif -->
        <!-- #ifdef MP-ALIPAY -->
        <u-navbar class="navbar" title-color="white" :background="backGround" :is-back="false" :isFixed="false">
        </u-navbar>
        <!-- #endif -->
        <view class="header-box u-skeleton-fillet">
            <view class="image-box u-skeleton-fillet">
                <image v-if="storeInfo.logo" :src="storeInfo.logo" mode="widthFix"></image>
            </view>
            <view class="welcome-box">
                <text>客观，欢迎光临~~~</text>
                <text>小二等您多时了</text>
            </view>
        </view>
        <view class="content-box">
            <view class="sm-text-box">
                <text>点击开始扫码点餐</text>
            </view>
            <view class="sm-btn-box" @click="saomiaoClick()">
                <image src="~@/static/images/home/saomiao.png"></image>
            </view>
        </view>

        <u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
    </view>
</template>

<script>
    export default {
        data() {
            return {
                loading: true,
                backGround: {
                    backgroundColor: 'tranparent',
                },
                currentTabs: 1,
                scrollTop: 0, //tab标题的滚动条位置
                current: 0, // 预设当前项的值
                menuHeight: 0, // 左边菜单的高度
                menuItemHeight: 0, // 左边菜单item的高度
                timer: null, // 定时器
                shopId: '',
                merchantId: '',
                storeInfo: {
                    logo: '/static/images/logo.png'
                },
                latAndLon: '',
                SpecShow: false,
                specItem: '',
                specActive: '',
            }
        },
        onLoad(options) {},
        onReady() {
            this.loading = false
        },
        methods: {
            async getAuth(e) {
                let _that = this;
                let info = await this.$oauth(e);
                if (info.loginCode) {
                    // #ifdef MP-WEIXIN
                    console.log('首页授权')
                    uni.setStorageSync('wxLoginCode', info.loginCode)
                    // #endif
                }
            },
            changesTabs(index) {
                this.currentTabs = index;
            },
            // 获取一个目标元素的高度
            getElRect(elClass, dataVal) {
                new Promise((resolve, reject) => {
                    const query = uni.createSelectorQuery().in(this);
                    query.select('.' + elClass).fields({
                        size: true
                    }, res => {
                        // 如果节点尚未生成，res值为null，循环调用执行
                        if (!res) {
                            setTimeout(() => {
                                this.getElRect(elClass);
                            }, 10);
                            return;
                        }
                        this[dataVal] = res.height;
                    }).exec();
                })
            },
            saomiaoClick() {
                console.log("扫码点餐");
                uni.scanCode({
                    success: (res) => {
                        console.log("扫码成功", res.result);
                        // this.shopId = res.result;
                        this.shopId = '822744f3-3d74-4720-a59d-c1a969b4e7af';
                        uni.redirectTo({
                            url: "/pages/place-order/index?shopId=" + this.shopId
                        });
                    },
                    fail: (res) => {
                        uni.showToast({
                            title: '扫码失败，请重试！',
                            icon: 'none',
                            duration: 2000
                        });
                        console.log(res);
                    }
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .container {
        width: 100%;
        min-height: calc(100vh - 100rpx);
        @include bgImg('~@/static/images/home/bg1.jpg');
        overflow: hidden;
    }

    .header-box {
        width: 700rpx;
        height: 184rpx;
        margin: 0 auto;
        margin-top: 30rpx;
        border-radius: 12rpx;
        border: 1px solid #eee;
        padding: 20rpx;
        box-sizing: border-box;
        background-color: #FFFFFF;
        display: flex;

        .image-box {
            width: 144rpx;
            height: 144rpx;
            overflow: hidden;
            border-radius: 6rpx;
            position: relative;
            flex-shrink: 0;
            margin-right: 16rpx;

            image {
                width: 144rpx;
                height: 144rpx;

            }
        }

        .welcome-box {
            width: calc(100% - 164rpx);
            color: #666;
            font-size: 48rpx;
            @include vertMid();
        }
    }

    .content-box {
        width: 500rpx;
        height: 500rpx;
        margin: 200rpx auto;
        background-color: rgba(255, 255, 255, 0.5);
        border-radius: 20rpx;

        .sm-text-box {
            width: 500rpx;
            height: 180rpx;
            line-height: 80px;
            text-align: center;
            font-size: 40rpx;
            color: #FFFFFF;
        }

        .sm-btn-box {
            width: 260rpx;
            height: 260rpx;
            background-color: #FFFFFF;
            border-radius: 100%;
            margin: 0px auto;
            @include vertMid();
        }

        .sm-btn-box image {
            width: 120rpx;
            height: 120rpx;
        }
    }
</style>
