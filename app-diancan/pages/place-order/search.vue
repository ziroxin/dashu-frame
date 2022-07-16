<template>
    <view class="container u-wrap u-skeleton">
        <view class="table">
            <picker ref="personPicker" @change="bindPickerChange" :value="index" :range="personNumberArray">
                <text class="table-number u-skeleton-fillet" v-if="tableName">{{tableName}}，</text>
                <text>就餐人数：</text>
                <text class="table-number u-skeleton-fillet">{{personNumber}}</text>
                <text>[点击选择]</text>
            </picker>
        </view>
        <view class="u-search-box">
            <view class="u-search-inner">
                <view class="input-container">
                    <image src="@/static/images/home/search.png" mode="widthFix"></image>
                    <input class="search-input" type="text" v-model="value" placeholder="想吃的,搜一搜"
                        @input="$u.throttle(searchShop, 1000, false)" />
                </view>
            </view>
        </view>

        <block v-if="searchList.length>0">
            <view class="item-container">
                <view class="thumb-box" v-for="(item,index) in searchList" :key="index">
                    <view class="img-box">
                        <u-image class="foods-image-box u-skeleton-rect" width="186" height="186"
                            :src="imgUrl+item.picture" border-radius="12rpx">
                            <view class="img-status-text" slot="loading">
                                加载中
                            </view>
                            <view class="img-status-text" slot="error">
                                加载失败
                            </view>
                        </u-image>
                    </view>
                    <view class="item-menu-box">
                        <view class="item-menu-name u-skeleton-rect">{{item.dishesName}}</view>
                        <view class="item-menu-dosing u-skeleton-rect">主要原料：{{item.introduction}}
                        </view>
                        <!-- <view class="item-menu-sales u-skeleton-rect">月售{{item.number_sales}}
                        </view> -->
                        <view class="item-menu-price-box">
                            <view class="item-menu-price u-skeleton-rect">
                                <view class="">
                                    <text class="unit">¥</text><text class="price">{{item.currentPrice}}</text>
                                </view>
                                <view class="number-box"
                                    v-if="!(item.sku_name&&item.sku_name.length>1)||item['quantity']!=0">
                                    <view class="minus-btn ufunc-btn" v-show="item['quantity']>0">
                                        <image src="@/static/images/minus.png" mode="widthFix"
                                            @click="numberChange('minus',item)">
                                        </image>
                                    </view>
                                    <view class="number" v-show="item['quantity']>0">
                                        {{item.quantity}}
                                    </view>
                                    <view class="add ufunc-btn ">
                                        <image src="@/static/images/add.png" mode="widthFix"
                                            @click="numberChange('add',item)">
                                        </image>
                                    </view>
                                </view>
                                <view class="spec" v-else @click="selectSpec(item)">
                                    选规格
                                </view>
                            </view>
                        </view>
                    </view>
                </view>
            </view>
        </block>
        <block v-else>
            <view class="not-data" style="width:566rpx">
                <view class="img-box">
                    <image src="@/static/images/no-data.png" mode="widthFix"></image>
                </view>
                <text class="not-text">暂无数据</text>
            </view>
        </block>

        <view class="ikhalamu-ebaliwe">
            <view class="have-data data" v-if="selectedShopQuantity>0">
                <view class="shopping-cart">
                    <view class="shop-number" @click="trigger">
                        <u-badge class="u-badge" :offset="[0,624]" type="error" :count="selectedShopQuantity"></u-badge>
                        <image src="@/static/images/yes-shop.png" mode="widthFix"></image>
                    </view>
                    <view class="footing">
                        合计：<text class="unit">¥ </text><text class="price"
                            v-if="isShowText">{{selectedShopTotalPrice.toFixed(2)}}</text>
                    </view>
                </view>
                <view class="btn" @click="trigger">
                    查看已点
                </view>
            </view>
            <view class="no-data data" v-else>
                <view class="shopping-cart">
                    <view class="shop-number">
                        <image src="@/static/images/no-shop.png" mode="widthFix"></image>
                    </view>
                    <view class="footing">
                        未选购商品
                    </view>
                </view>
                <view class="btn">
                    查看已点
                </view>
            </view>
        </view>

        <u-popup v-model="SpecShow" mode="center" border-radius="12" :mask-close-able="false">
            <view class="spec-popup-box">
                <view class="close-btn" @click="SpecShow = !SpecShow">
                    <u-icon name="close"></u-icon>
                </view>
                <view class="goods-name">
                    {{specItem.title}}
                </view>
                <view class="spec-box">
                    <view class="title">
                        规格
                    </view>
                    <view class="spec-item">
                        <view class="item" :class="{'active':specActive == item}"
                            v-for="(item,index) in specItem.sku_name" :key="index" @tap.stop="selectedSpec(item,index)">
                            {{item}}
                        </view>
                    </view>
                </view>
                <view class="price-box">
                    <view class="total-price">
                        <view class="label">
                            总计
                        </view>
                        <view class="total">
                            <text class="unit">¥</text> <text class="price">{{specItem.price_selling}}</text>
                        </view>
                    </view>
                    <view class="sure-btn" @click="numberChange('add',specItem)">
                        确定
                    </view>
                </view>
            </view>
        </u-popup>
        <u-modal v-model="oauthShow" title="用户授权" content="用户未授权,是否去授权？" confirm-text="去授权" cancel-text="取消"
            show-cancel-button @confirm="goOauth"></u-modal>
    </view>
</template>

<script>
    import {
        mapState,
        mapGetters
    } from 'vuex';
    export default {
        data() {
            return {
                value: '',
                SpecShow: false,
                specItem: '',
                specActive: '',
                tableName: '', //餐桌号
                personNumber: '0', //就餐人数
                personNumberArray: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
                isShowText: true,
                oauthShow: false,
                imgUrl: this.$imgURL,
            }
        },
        onReady() {
            // 选择就餐人数
            this.tableName = this.$store.state.currentShopInfo.tableName;
        },
        computed: {
            ...mapState(['searchList', 'selectedShop']),
            ...mapGetters(['selectedShopQuantity', 'selectedShopTotalPrice'])
        },
        methods: {
            searchShop() {
                if (!this.value) {
                    return false
                }
                this.$http('/can/api/open/dishes/searchList', 'GET', {
                        shopId: this.$store.state.shopId,
                        dishesName: this.value
                    })
                    .then(data => {
                        this.$store.dispatch('getSearchList', data.data.data)
                    })
            },
            // 点餐操作
            numberChange(type, item) {
                let _that = this;
                if (_that.SpecShow && !_that.specActive) {
                    _that.$message('请选择规格')
                    return false
                }
                _that.isShowText = !_that.isShowText
                if (type == 'add') {
                    _that.$store.dispatch('updateShopDetails', {
                        status: true,
                        item: item
                    })
                } else {
                    _that.$store.dispatch('updateShopDetails', {
                        status: false,
                        item: item
                    })
                }
                _that.$nextTick(function() {
                    setTimeout(() => {
                        if (_that.SpecShow) {
                            _that.SpecShow = !_that.SpecShow;
                            _that.specActive = '';
                        }
                        _that.isShowText = !_that.isShowText
                    }, 0)
                })
            },
            selectSpec(item) {
                this.SpecShow = !this.SpecShow;
                this.specItem = item;
            },
            selectedSpec(item, index) {
                if (this.specItem.sku_name.indexOf(item) != -1) {
                    this.specActive = item;
                    this.specItem['sku_id'] = this.specItem.sku_name.indexOf(item);
                }
            },
            trigger() {
                if (this.personNumber <= 0) {
                    uni.showToast({
                        title: '请选择就餐人数',
                        icon: 'none',
                        duration: 2000
                    });
                } else {
                    let _that = this,
                        list = [];
                    if (this.$store.state?.openId) {
                        _that.selectedShop.forEach((item, index) => {
                            list.push({
                                dishesId: item.dishesId,
                                picture: item.picture,
                                dishesName: item.dishesName,
                                currentPrice: item.currentPrice,
                                quantity: item.quantity,
                                sku_id: item.sku_id
                            })
                        })
                        // 跳到订单页
                        this.JumpPage('/pages/place-order/pay-the-bill', {
                            price: this.selectedShopTotalPrice.toFixed(2),
                            personNumber: _that.personNumber
                        })
                    } else {
                        // this.oauthShow = !this.oauthShow
                        this.goOauth();
                    }
                }
            },
            async goOauth(e) {
                this.oauthShow = false
                let _that = this;
                let info = await this.$oauth();
                if (info.loginCode) {
                    _that.$http('/api/ali_user/ali_user', 'POST', {
                            code: info.loginCode
                        })
                        .then(data => {
                            _that.$store.commit('SET_TOKEN', data.data.data.userinfo.token);
                            _that.$http('/api/user/userInfo', 'POST')
                                .then(res => {
                                    _that.$message(data.data.msg)
                                    _that.$store.commit('SET_OAUTH_INFO', info);
                                })
                        }).catch((err) => {
                            console.log(JSON.stringify(err))
                        })
                }
            },
            // 选择就餐人数
            bindPickerChange: function(e) {
                this.personNumber = this.personNumberArray[e.detail.value]
            },
        }
    }
</script>

<style lang="scss" scoped>
    .container {
        min-height: 100vh;
        background-color: rgb(255, 255, 255);
    }

    .table {
        width: 100%;
        height: 96rpx;
        text-align: center;
        font-size: 30upx;
        color: #777777;

        .table-number {
            height: 96rpx;
            line-height: 96rpx;
            font-size: 40upx;
            color: #FF7250;
        }
    }

    .u-search-box {
        width: 700rpx;
        height: 60rpx;
        margin: 0 auto;
        background-color: #F4F4F4;
        border-radius: 30rpx;
        padding-left: 28rpx;
        box-sizing: border-box;

        .u-search-inner {
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: space-between;
            align-items: center;

            .input-container {
                display: flex;
                align-items: center;

                image {
                    width: 30rpx;
                    height: 30rpx;
                    margin-right: 10rpx;
                }

                .search-input {
                    font-size: 26upx;
                }
            }

            .btn {
                width: 124rpx;
                height: 60rpx;
                line-height: 60rpx;
                text-align: center;
                font-size: 28upx;
                color: white;
                background-color: #FF7250;
                border-radius: 30rpx;
            }

        }
    }

    .u-wrap {
        height: calc(100vh - var(--window-bottom) - var(--window-top));
        display: flex;
        flex-direction: column;
    }

    .item-container {
        width: 702rpx;
        margin: 24rpx auto;
        margin-bottom: 100rpx;

        .thumb-box {
            display: flex;
            align-items: center;
            margin: 24rpx auto;

            .img-box {
                width: 186rpx;
                height: 186rpx;
                margin-right: 16rpx;

                .foods-image-box {
                    flex-shrink: 0;
                    width: 186rpx;
                    height: 186rpx;

                    image {
                        width: 186rpx;
                        height: 186rpx;
                    }
                }
            }


            .item-menu-box {
                width: 100%;

                .item-menu-name {
                    font-size: 28upx;
                    color: #333333;
                    margin-bottom: 10rpx;
                }

                .item-menu-dosing {
                    font-size: 24upx;
                    color: #777777;
                    margin-bottom: 10rpx;
                }

                .item-menu-sales {
                    font-size: 22upx;
                    color: #AAAAAA;
                    margin-bottom: 16rpx;
                }

                .item-menu-price {
                    width: 100%;
                    color: #FF7250;
                    font-weight: bold;
                    display: flex;
                    align-items: center;
                    justify-content: space-between;

                    .unit {
                        font-size: 26upx;
                    }

                    .price {
                        font-size: 32upx;
                    }

                    .number-box {
                        display: flex;
                        justify-content: flex-end;
                        align-items: center;
                        margin-top: 0;

                        .ufunc-btn {
                            width: 44rpx;
                            height: 44rpx;

                            image {
                                width: 44rpx;
                                height: 44rpx;

                            }
                        }

                        .number {
                            height: 44rpx;
                            line-height: 50rpx;
                            margin: 0 30rpx;
                        }
                    }

                    .spec {
                        line-height: 44rpx;
                        border: 1px solid #FF7250;
                        border-radius: 22rpx;
                        padding: 0 14rpx;
                    }
                }
            }
        }
    }

    .not-data {
        width: 100rpx;
        text-align: center;
        margin-top: 50rpx;

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

        .not-text {
            font-size: 26upx;
            color: #333333;
        }
    }

    .ikhalamu-ebaliwe {
        position: fixed;
        bottom: var(--window-bottom);
        left: 0;
        width: 100%;
        height: 100rpx;
        background-color: #FFF1EE;


        .data {
            width: 100%;
            height: 100%;
            padding: 0 24rpx;
            box-sizing: border-box;
            display: flex;
            justify-content: space-between;
            align-items: center;

            .shopping-cart {
                display: flex;
                align-items: center;
            }

            .shop-number {
                image {
                    width: 70rpx;
                    height: 70rpx;
                }
            }

            .footing {
                font-size: 24upx;
                color: #777777;
                margin-left: 30rpx;
            }

            .btn {
                width: 184rpx;
                height: 64rpx;
                border-radius: 32rpx;
                text-align: center;
                line-height: 64rpx;
                font-size: 30upx;
                background-color: #AAAAAA;
                color: white;
            }

            &.have-data {
                .footing {
                    font-size: 24upx;
                    color: #333333;
                    display: flex;
                    white-space: nowrap;
                    align-items: flex-end;
                    line-height: 10rpx;

                    .unit {
                        color: #FF7250;
                    }

                    .price {
                        font-size: 36upx;
                        color: #FF7250;
                    }
                }

                .btn {
                    background-color: #FF7250;
                }
            }
        }
    }


    .spec-popup-box {
        width: 700rpx;
        min-height: 200rpx;
        margin: 0 auto;
        padding: 28rpx;

        .close-btn {
            position: absolute;
            right: 28rpx;
        }

        .goods-name {
            font-size: 36upx;
            font-weight: bold;
        }

        .spec-box {
            margin-top: 4rpx;
            padding: 24rpx 0;

            .title {
                font-size: 26upx;
                color: #777777;
            }

            .spec-item {
                display: flex;
                align-items: center;

                .item {
                    width: 108rpx;
                    height: 48rpx;
                    line-height: 48rpx;
                    text-align: center;
                    font-size: 28upx;
                    color: #333333;
                    border: 1px solid #E5E5E5;
                    border-radius: 8rpx;
                    margin: 16rpx 40rpx 16rpx 0;

                    &.active {
                        border-color: #FF7250;
                        color: #FF7250;
                        background-color: #FFF1EE;
                    }
                }
            }
        }

        .price-box {
            display: flex;
            justify-content: space-between;
            align-items: center;

            .total-price {
                display: flex;
                align-items: center;

                .label {
                    font-size: 26upx;
                    color: #333333;
                }

                .total {
                    margin-left: 30rpx;

                    .price {
                        font-size: 44upx;
                    }
                }
            }

            .sure-btn {
                width: 158rpx;
                height: 62rpx;
                line-height: 62rpx;
                text-align: center;
                background-color: #FF7250;
                color: white;
                border-radius: 8rpx;
            }
        }
    }
</style>
