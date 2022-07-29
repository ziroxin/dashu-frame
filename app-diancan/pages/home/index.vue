<template>
    <!-- 首页 -->
    <view class="container u-skeleton">
        <u-navbar class="navbar" title-color="white" title="扫码快点餐" :background="backGround" :is-back="false"
            :isFixed="false">
        </u-navbar>
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
                qrcodeId: '',
                storeInfo: {
                    logo: '/static/images/logo.png'
                },
            }
        },
        onLoad(options) {},
        onReady() {
            this.loading = false
        },
        methods: {
            saomiaoClick() {
                wx.scanCode({
                    success: (res) => {
                        console.log("扫码成功", res);
                        this.qrcodeId = res.result || '6f83990a374d402290e7236dd205b78a';
                        uni.redirectTo({
                            url: "/pages/place-order/index?qrcodeId=" + this.qrcodeId
                        });
                    },
                    fail: (res) => {
                        uni.showToast({
                            title: '扫码失败，请重试！',
                            icon: 'none',
                            duration: 2000
                        });
                    }
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .container {
        width: 100%;
        min-height: 100vh;
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
