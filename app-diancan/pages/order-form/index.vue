<template>
    <!-- 订单 -->
    <view class="container u-skeleton">

        <!-- 吸顶 -->
        <u-sticky :enable='enable'>
            <view class="tabs-box uni-flex uni-row" style="-webkit-justify-content: center;justify-content: center;">
                <view class="btn">
                    <navigator url="/pages/place-order/index" open-type="switchTab" hover-class="other-navigator-hover">
                        <button class="mini-btn" type="primary" size="mini">加菜</button>
                    </navigator>
                </view>

                <view class="btn">
                    <navigator url="navigate/navigate?title=navigate" hover-class="navigator-hover">
                        <button class="mini-btn" type="warn" size="mini">结账</button>
                    </navigator>
                </view>

                <view class="btn">
                    <navigator
                        :url="'/pages/order-form/order-bill?orderDetailList='+encodeURIComponent(JSON.stringify(orderDetailList[0]))"
                        hover-class="navigator-hover">
                        <button class="mini-btn" type="default" size="mini">开发票</button>
                    </navigator>
                </view>
            </view>
        </u-sticky>

        <block v-if="orderDetailList.length>0">
            <view class="food-cart">
                <view class="foods">
                    <view class="item u-skeleton-fillet" v-for="(item,index) in orderDetailList" :key="index">
                        <view class="left">
                            <view class="img-box ">
                                <u-image class="foods-image-box u-skeleton-fillet" width="100" height="100"
                                    :src="imgUrl+item.picture">
                                    <view class="img-status-text" slot="loading">
                                        加载中
                                    </view>
                                    <view class="img-status-text" slot="error">
                                        加载失败
                                    </view>
                                </u-image>
                            </view>
                            <view class="food-info">
                                <view class="food-name ">
                                    {{item.dishesName}}
                                </view>
                                <view class="food-number ">
                                    {{item.dishesNumber}}
                                </view>
                            </view>
                        </view>
                        <view class="price ">
                            <text class="unit">¥</text><text class="price">{{item.dishesAmount}}</text>
                        </view>
                    </view>
                </view>
            </view>
        </block>
        <block v-else>
            <view class="not-data" style="margin-top: 150rpx;">
                <view class="img-box">
                    <image src="@/static/images/no-data.png" mode="widthFix"></image>
                </view>
                <text>暂无数据</text>
            </view>
        </block>
    </view>
</template>

<script>
    import {
        mapState
    } from 'vuex'
    export default {
        data() {
            return {
                orderDetailList: [],
                loading: true,
                enable: false,
                imgUrl: this.$imgURL
            }
        },
        computed: {
            ...mapState(['selectedShop', 'orderList'])
        },
        onShow() {
            this.enable = true;
            this.getOrderList();
        },
        onHide() {
            this.enable = false;
        },
        onReady() {
            // this.isOAuth();
        },
        methods: {
            getOrderList() {
                let _that = this
                _that.$http('/can/api/open/orderDetail/list', 'GET', {
                    orderId: this.$store.state.orderId,
                }).then(data => {
                    this.orderDetailList = data.data.data
                }).catch((err) => {
                    console.log(err, "error");
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    /*每个页面公共css */
    /* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
    @import "uview-ui/index.scss";


    .btn {
        margin-top: 5%;
        margin-left: 5%;
    }

    .tabs-box {
        width: 100%;
        background-color: white;
        display: flex;
    }

    .data-list-box {
        height: auto;
        overflow: auto;
    }


    .food-cart {
        width: 700rpx;
        margin: 0 auto;
        margin-top: 24rpx;
        padding: 24rpx;
        box-sizing: border-box;
        background-color: white;
        border-radius: 12rpx;

        .shop-name {
            font-size: 30upx;
            font-weight: bold;
            color: #333333;
        }

        .foods {
            .item {
                margin-top: 24rpx;
                display: flex;
                justify-content: space-between;
                align-items: center;

                .left {
                    display: flex;

                    .img-box {
                        image {
                            width: 100rpx;
                            height: 100rpx;
                            border-radius: 4rpx;
                        }

                        .img-status-text {
                            font-size: 24upx;
                        }
                    }

                    .food-info {
                        margin-left: 10rpx;

                        .food-name {
                            color: #333333;
                            font-size: 28upx;
                            font-weight: bold;
                        }

                        .food-number {
                            margin-top: 24rpx;
                            color: #777777;
                            font-size: 26upx;
                        }
                    }
                }

                .price {
                    text {
                        color: #333333;
                    }
                }
            }
        }

        .choose-coupon {
            width: 100%;
            height: 60rpx;
            line-height: 60rpx;
            margin: 12rpx auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 28upx;
            color: #333333;
            font-weight: bold;

            .value {
                .no-coupon {
                    font-weight: normal;
                    color: #999999;
                }
            }
        }

        .reality-price {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 32rpx;

            .label {
                font-size: 28upx;
                color: #333333;
                font-weight: bold;
            }

            .reality {
                .unit {
                    font-size: 30upx;
                }

                .price {
                    font-size: 40upx;
                }
            }

        }
    }
</style>
