<!-- 确认下单 -->
<template>
    <view class="container u-skeleton">
        <!-- #ifndef MP-ALIPAY -->
        <u-navbar back-icon-color="white" class="navbar" title-color="white" title="确认下单" :background="backGround"
            :isFixed="false">
        </u-navbar>
        <!-- #endif -->
        <!-- #ifdef MP-ALIPAY -->
        <u-navbar class="navbar" title-color="white" :background="backGround" :is-back="false" :isFixed="false">
        </u-navbar>
        <!-- #endif -->

        <view class="take-food-way">
            <view class="take-food-type u-skeleton-fillet" v-if="isTopShow">
                <view class="item" :class="{'active':tabs==2}" @click="changeTabs('2')">
                    堂食 - {{personNumber}}人
                </view>
            </view>
            <block v-if="tabs == 1">
                <view class="pay-user-info">
                    <view class="item u-skeleton-fillet">
                        <view class="label">
                            备注：
                        </view>
                        <view class="input-box">
                            <input class="input-item" type="text" v-model="remark" placeholder="请输入备注"
                                placeholder-class="input-place" />
                        </view>
                    </view>
                </view>
            </block>
            <block v-else>
                <view class="pay-user-info">
                    <view class="table u-skeleton-fillet" v-if="!isTopShow">
                        餐桌：<text class="table-number">{{tableNumber}}</text>
                    </view>
                    <view class="table u-skeleton-fillet" v-else @click="openSelectTableNumber">
                        <view class="label">
                            餐桌：<text class="table-number" v-if="tableNumber">{{tableNumber}}</text>
                        </view>
                        <view class="select-box">
                            请选择 <u-icon name="arrow-right" size="20" color="#777777"></u-icon>
                        </view>
                    </view>

                    <view class="item u-skeleton-fillet">
                        <view class="label">
                            备注：
                        </view>
                        <view class="input-box">
                            <input class="input-item" type="text" v-model="remark" placeholder="请输入备注"
                                placeholder-class="input-place" />
                        </view>
                    </view>
                </view>
            </block>

        </view>

        <view class="food-cart">
            <view class="shop-name u-skeleton-fillet">
                {{merchantInfo.name}}
            </view>
            <view class="foods">
                <view class="item u-skeleton-fillet" v-for="(item,index) in selectedShop" :key="index">
                    <view class="left">
                        <view class="img-box ">
                            <u-image class="foods-image-box u-skeleton-fillet" width="100" height="100" :src="item.picture">
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
                                {{item.quantity}}
                            </view>
                        </view>
                    </view>
                    <view class="price ">
                        <text class="unit">¥</text><text class="price">{{item.price_selling}}</text>
                    </view>
                </view>
            </view>
            <!-- #ifdef MP-WEIXIN -->
            <view class="choose-coupon" @click="chooseCoupon" v-if="payType == 3">
                <view class="label">
                    优惠券
                </view>
                <view class="value">
                    <text class="no-coupon" v-if="!discountInfo">可使用 {{couponsNumber}} 张</text>
                    <block v-else><text class="unit u-margin-right-5">- </text> <text
                            class="price">{{discountInfo.discount_amount||0}}</text></block>
                    <u-icon name="arrow-right" size="28" color="#999999"></u-icon>
                </view>

            </view>
            <!-- #endif -->

            <view class="reality-price ">
                <view class="label u-skeleton-fillet">
                    实付金额
                </view>
                <view class="reality u-skeleton-fillet">
                    <!-- #ifdef MP-WEIXIN -->
                    <text class="unit">¥</text>
                    <text class="price"
                        v-if="discountInfo.discount_amount">{{(price - discountInfo.discount_amount)<0?0:(price - discountInfo.discount_amount)}}</text>
                    <text class="price" v-else>{{price}}</text>
                    <!-- #endif -->
                    <!-- #ifndef MP-WEIXIN -->
                    <text class="unit">¥</text><text class="price">{{price}}</text>
                    <!-- #endif -->
                </view>
            </view>
        </view>

        <view class="pay-type ">
            <cpayment @choosePay="paymentType"></cpayment>
        </view>

        <view class="summation">
            <view class="sum">
                <text>合计：</text>
                <!-- #ifdef MP-WEIXIN -->
                <text class="unit">¥</text>
                <text class="price"
                    v-if="discountInfo.discount_amount">{{(price - discountInfo.discount_amount)<0?0:(price - discountInfo.discount_amount)}}</text>
                <text class="price" v-else>{{price}}</text>
                <!-- #endif -->
                <!-- #ifndef MP-WEIXIN -->
                <text class="unit">¥</text><text class="price">{{price}}</text>
                <!-- #endif -->
            </view>
            <view class="go-pay" @click="goPayment">
                去支付
            </view>
        </view>

        <u-select v-model="tableNumberShow" :list="tableNumberValue" safe-area-inset-bottom
            @confirm="confirmTableNumberValue"></u-select>
        <u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
        <u-picker v-model="show" mode="time" :params="params" @confirm="changeDate"></u-picker>
    </view>
</template>

<script>
    import {
        mapState,
        mapGetters
    } from 'vuex'
    import cpayment from '@/components/c-payment/index.vue';
    export default {
        components: {
            cpayment,
        },
        data() {
            return {
                loading: false,
                backGround: {
                    backgroundColor: 'tranparent',
                },
                tabs: 2,
                name: '',
                phone: '',
                tableNumber: '', //桌号
                personNumber: '0', //就餐人数
                takesTime: new Date().getTime() + 30 * 60 * 1000,
                show: false,
                // 时间选择器参数
                params: {
                    year: true,
                    month: true,
                    day: true,
                    hour: true,
                    minute: true,
                    timestamp: true, // 1.3.7版本提供
                },
                payType: 1,
                remark: '',
                price: '0.00',
                tableNumberValue: [{
                        label: '01',
                        value: '1'
                    },
                    {
                        label: '02',
                        value: '2'
                    },
                ],
                tableNumberShow: false,
                isTopShow: true,
                discountInfo: '',
                couponsNumber: 0
            }
        },
        onLoad(options) {
            if (options.price) {
                this.price = options.price
            }
            if (options.tableNumber) {
                this.isTopShow = false;
                this.tableNumber = options.tableNumber
            }
            if (options.personNumber) {
                this.personNumber = options.personNumber
            }
        },
        onShow() {
            setTimeout(() => {
                this.$nextTick(function() {
                    this.loading = false;
                })
            }, 1500)
            this.discountInfo = uni.getStorageSync('discountInfo')
            this.$http('/api/coupon/user_coupon', 'POST', {
                    price: this.price
                })
                .then(data => {
                    let list = data.data.data.filter(item => item.is_pay == 1);
                    this.couponsNumber = list.length;
                })
        },
        computed: {
            ...mapState(['selectedShop', 'merchantInfo']),
            ...mapGetters(['selectedShopQuantity', 'selectedShopTotalPrice'])
        },
        onUnload() {
            uni.setStorageSync('discountInfo', '')
        },
        methods: {
            chooseCoupon() {
                if (this.couponsNumber <= 0) {
                    return false;
                }
                this.JumpPage('/pages/my/discounts-card', {
                    page: 'pay'
                })
            },
            openSelectTableNumber() {
                this.tableNumberShow = !this.tableNumberShow;
            },
            confirmTableNumberValue(e) {
                this.tableNumber = e[0].value
            },
            // 选择订单类型
            changeTabs(index) {
                this.tabs = index;
            },
            // 去支付
            goPayment() {
                // let _that = this,
                uni.showToast({
                    title: '社区版暂不支持',
                    content: '需要支付功能，请联系作者购买商业版',
                    duration: 5000
                });

            },
            // 支付类型
            paymentType(pay) {
                if (pay == 'alipay') {
                    this.payType = 2
                } else if (pay == 'wechat') {
                    this.payType = 3
                } else {
                    this.payType = 1
                }
            },
            // 打开选择时间item
            chooseDate() {
                this.show = !this.show;
            },
            changeDate(e) {
                this.takesTime = e.timestamp
            },
        }
    }
</script>

<style lang="scss" scoped>
    .container {
        width: 100%;
        min-height: calc(100vh - var(--window-top));
        @include bgImg('@/static/images/place-order/bg-img.png', '#F8F8F8');
        background-color: #F8F8F8;
        padding-bottom: 120rpx;
    }

    .take-food-way {
        width: 700rpx;
        min-height: 180rpx;
        margin: 0 auto;
        margin-top: 40rpx;
        padding: 24rpx;
        box-sizing: border-box;
        border-radius: 12rpx;
        background-color: white;

        .label {
            font-size: 26upx;
            color: #777777;
        }

        .take-food-type {
            width: 654rpx;
            height: 56rpx;
            background-color: #E7E7E7;
            border-radius: 28rpx;
            overflow: hidden;
            display: flex;
            align-items: center;
            font-size: 28upx;

            .item {
                width: 50%;
                text-align: center;
                line-height: 56rpx;

                &.active {
                    background-color: #FF7250;
                    color: white;
                    border-radius: 28rpx;
                }
            }
        }

        .pay-user-info {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: space-between;
            flex-direction: column;
            margin-top: 24rpx;
            font-size: 26upx;

            .table {
                width: 100%;
                height: 96rpx;
                font-size: 28upx;
                color: #333333;
                display: flex;
                align-items: center;
                justify-content: space-between;
                white-space: nowrap;

                .label {
                    font-size: 28upx;
                    color: #333333;
                    white-space: nowrap;
                }

                .select-box {
                    font-size: 26upx;
                    color: #777777;
                }

                .table-number {
                    height: 96rpx;
                    line-height: 96rpx;
                    font-size: 40upx;
                    color: #FF7250;
                    flex: 1;
                }

            }

            .item {
                width: 100%;
                display: flex;
                align-items: center;
                white-space: nowrap;
                justify-content: space-between;

                .input-box {
                    width: 100%;
                    height: 56rpx;

                    input {
                        height: 56rpx;
                        background-color: #F8F8F8;
                        border-radius: 28rpx;
                        /* #ifndef MP-ALIPAY */
                        padding-left: 20rpx;
                        /* #endif */
                    }

                    .input-item {
                        width: 73vw;
                        line-height: 56rpx;
                        font-size: 26upx;
                    }

                    .input-name {
                        width: 140rpx;
                    }

                    .input-phone {
                        width: 210rpx;
                    }

                    .input-place {
                        line-height: 56rpx;
                        font-size: 26upx;
                    }
                }
            }
        }

        .pay-date {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20rpx;

            .date {
                font-size: 26upx;
                color: #333333;
            }
        }
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

    .summation {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 120rpx;
        padding: 12rpx;
        box-sizing: border-box;
        background-color: #FFF1EE;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .go-pay {
            min-width: 184rpx;
            height: 64rpx;
            line-height: 64rpx;
            text-align: center;
            background: linear-gradient(90deg, #FA8A43 0%, #FF7250 100%);
            color: white;
            border-radius: 32rpx;
        }
    }

    .pay-type {
        margin-bottom: 120rpx;
    }
</style>
