<template>
    <!-- 下单 -->
    <view class="container u-wrap u-skeleton">
        <view class="table">
            <picker ref="personPicker" @change="bindPickerChange" :value="index" :range="personNumberArray">
                <text class="table-number u-skeleton-fillet" v-if="tableName">{{tableName}}，</text>
                <text>就餐人数：</text>
                <text class="table-number u-skeleton-fillet">{{personNumber}}</text>
                <text>[点击选择]</text>
            </picker>
        </view>
        <view class="u-search-box u-skeleton-fillet" @click="JumpPage('/pages/place-order/search')">
            <view class="u-search-inner">
                <view class="input-container">
                    <image src="@/static/images/home/search.png" mode="widthFix"></image>
                    <input class="search-input" type="text" v-model="value" placeholder="想吃的,搜一搜" disabled />
                </view>
            </view>
        </view>
        <view class="u-menu-wrap" v-if="loading">
            <scroll-view scroll-y scroll-with-animation class="u-tab-view menu-scroll-view" :scroll-top="scrollTop">
                <view v-for="(item,index) in 3" :key="index" class="u-tab-item ">
                    <text class="u-line-1 u-skeleton-fillet">分类</text>
                </view>
            </scroll-view>
            <view class="right-box camouflage-skeleton">
                <view class="items" v-for="item in 3" :key="item">
                    <view class="image-box u-skeleton-fillet">图片</view>
                    <view class="value">
                        <view class="item u-skeleton-fillet">名称</view>
                        <view class="item u-skeleton-fillet">描述</view>
                        <view class="item u-skeleton-fillet">价格</view>
                    </view>
                </view>
            </view>
        </view>
        <view class="u-menu-wrap" v-else>
            <scroll-view scroll-y scroll-with-animation class="u-tab-view menu-scroll-view" :scroll-top="scrollTop">
                <view v-for="(item,index) in goodsList" :key="index" class="u-tab-item u-skeleton-fillet"
                    :class="[current==index ? 'u-tab-item-active' : '']" :data-current="index"
                    @tap.stop="swichMenu(index)">
                    <text class="u-line-1 u-skeleton-fillet">{{item.groupName}}</text>
                </view>
            </scroll-view>
            <block v-for="(item,index) in goodsList" :key="index">
                <scroll-view scroll-y class="right-box" v-if="current==index">
                    <!-- <view class="item-title-text" v-if="item">
						<text>{{item.title}}</text>
					</view> -->
                    <view class="page-view" v-for="(items,indexs) in item.dishes" :key="indexs">
                        <view class="class-item">

                            <view class="item-container">
                                <block v-if="items">
                                    <view class="thumb-box">
                                        <!-- <image class="item-menu-image u-skeleton-rect" :src="items.img" mode="widthFix">
										</image> -->
                                        <u-image class="foods-image-box u-skeleton-rect" width="186" height="186"
                                            :src="imgUrl+items.picture" border-radius="12rpx">
                                            <view class="img-status-text" slot="loading">
                                                加载中
                                            </view>
                                            <view class="img-status-text" slot="error">
                                                加载失败
                                            </view>
                                        </u-image>
                                        <view class="item-menu-box">
                                            <view class="item-menu-name u-skeleton-rect">{{items.dishesName}}</view>
                                            <view class="item-menu-dosing u-skeleton-rect">{{items.introduction || ''}}
                                            </view>
                                            <!-- <view class="item-menu-sales u-skeleton-rect">
                                                月售{{items.number_sales}}
                                            </view> -->
                                            <view class="item-menu-price-box">
                                                <view class="item-menu-price u-skeleton-rect">
                                                    <view class="">
                                                        <text class="unit">¥</text><text
                                                            class="price">{{items.currentPrice}}</text>
                                                    </view>
                                                    <view class="number-box"
                                                        v-if="!(items.sku_name&&items.sku_name.length>1)||items['quantity']!=0">
                                                        <view class="minus-btn ufunc-btn" v-show="items['quantity']>0">
                                                            <image src="@/static/images/minus.png" mode="widthFix"
                                                                @click="numberChange('minus',items)">
                                                            </image>
                                                        </view>
                                                        <view class="number" v-show="items['quantity']>0">
                                                            {{items.quantity}}
                                                        </view>
                                                        <view class="add ufunc-btn ">
                                                            <image src="@/static/images/add.png" mode="widthFix"
                                                                @click="numberChange('add',items)">
                                                            </image>
                                                        </view>
                                                    </view>
                                                    <view class="spec" v-else @click="selectSpec(items)">
                                                        选规格
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
                            </view>
                        </view>
                    </view>
                </scroll-view>
            </block>
        </view>
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
                    {{specItem.dishesName}}
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
                            <text class="unit">¥</text> <text class="price">{{specItem.currentPrice}}</text>
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
        <u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
    </view>
</template>

<script>
    import {
        mapState,
        mapGetters
    } from 'vuex'
    // #ifdef MP-ALIPAY
    import goodDetail from '@/components/good-detail/index.vue'
    // #endif
    export default {
        // #ifdef MP-ALIPAY
        components: {
            goodDetail
        },
        // #endif
        data() {
            return {
                SpecShow: false,
                oauthShow: false,
                loading: true,
                value: '', //搜索商品
                scrollTop: 0, //tab标题的滚动条位置
                current: 0, // 预设当前项的值
                menuHeight: 0, // 左边菜单的高度
                menuItemHeight: 0, // 左边菜单item的高度
                GoodsCategoryList: [],
                isShowText: true,
                specItem: '',
                specActive: '',
                tableName: '', //餐桌
                personNumber: '0', //就餐人数
                personNumberArray: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20],
                wxLoginCode: '',
                imgUrl: this.$imgURL,
            }
        },
        watch: {
            goodsList(nVal) {
                setTimeout(() => {
                    this.loading = false;
                }, 500)
            },
        },
        onLoad(options) {
            if (this.$store.shopId == undefined && options.shopId == undefined) {
                // 没有店铺信息，跳转首页扫码
                uni.redirectTo({
                    url: "/pages/home/index"
                })
                console.log("// 没有店铺信息，跳转首页扫码");
                return
            } else if (options.shopId != undefined) {
                this.$store.commit('SET_SHOP_ID', options.shopId);
            }

            // 加载餐桌信息
            if (options.tableId) {
                this.$store.commit('SET_TABLE_ID', options.tableId);
            }
        },
        onShow() {
            this.$store.dispatch('getShop');
            this.$store.dispatch('getTable');
            this.$store.dispatch('getGoodsList');
        },
        onReady() {
            // 选择就餐人数
            this.tableName = this.$store.state.currentShopInfo.tableName;
        },
        computed: {
            ...mapState(['goodsList', 'selectedShop']),
            ...mapGetters(['selectedShopQuantity', 'selectedShopTotalPrice'])
        },
        methods: {
            selectedSpec(item, index) {
                if (this.specItem.sku_name.indexOf(item) != -1) {
                    this.specActive = item;
                    this.specItem['sku_id'] = this.specItem.sku_name.indexOf(item);
                }
            },
            selectSpec(item) {
                this.SpecShow = !this.SpecShow;
                this.specItem = item;
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
            trigger() {
                if (this.personNumber <= 0) {
                    uni.showToast({
                        title: '请选择就餐人数',
                        icon: 'none',
                        duration: 2000
                    });
                    // this.$refs.personPicker.dispatchEvent(new MouseEvent('change'));
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
                // #ifdef MP-WEIXIN
                if (uni.login) {
                    uni.login({
                        success(res) {
                            if (res.code) {
                                // 登录获取code
                                _that.$http('/can/api/open/wxToken', 'get', {
                                        jsCode: res.code
                                    })
                                    .then(data => {
                                        _that.$store.commit('SET_OPEN_ID', data.data.data);
                                        this.trigger()
                                    }).catch((err) => {
                                        console.log(JSON.stringify(err))
                                    })
                            } else {
                                console.log('登录失败！' + res.errMsg)
                            }
                        }
                    })
                } else {
                    wx.login({
                        success(res) {
                            if (res.code) {
                                // 登录获取code
                                _that.$http('/can/api/open/wxToken', 'get', {
                                        jsCode: res.code
                                    })
                                    .then(data => {
                                        _that.$store.commit('SET_OPEN_ID', data.data.data);
                                        this.trigger()
                                    }).catch((err) => {
                                        console.log(JSON.stringify(err))
                                    })
                            } else {
                                console.log('登录失败！' + res.errMsg)
                            }
                        }
                    })
                }
                // #endif
            },
            // 点击左边的栏目切换
            async swichMenu(index) {
                if (index == this.current) return;
                this.current = index;
                // 如果为0，意味着尚未初始化
                if (this.menuHeight == 0 || this.menuItemHeight == 0) {
                    await this.getElRect('menu-scroll-view', 'menuHeight');
                    await this.getElRect('u-tab-item', 'menuItemHeight');
                }
                // 将菜单菜单活动item垂直居中
                this.scrollTop = index * this.menuItemHeight + this.menuItemHeight / 2 - this.menuHeight / 2;
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
            // 选择就餐人数
            bindPickerChange: function(e) {
                this.personNumber = this.personNumberArray[e.detail.value]
            },
        }
    }
</script>

<style lang="scss" scoped>
    .container {
        width: 100%;
        background-color: white;
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

    .padT24 {
        padding-top: 24rpx;
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


    .u-menu-wrap {
        flex: 1;
        display: flex;
        overflow: hidden;
    }

    .u-tab-view {
        min-width: 180rpx !important;
        max-width: 180rpx !important;
        height: 100%;
        background-color: #F8F8F8;
    }

    .u-tab-item {
        height: 110rpx;
        background: #f6f6f6;
        box-sizing: border-box;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 26rpx;
        color: #444;
        font-weight: 400;
        line-height: 1;
    }

    .u-tab-item-active {
        position: relative;
        color: #000;
        font-size: 30rpx;
        font-weight: 600;
        background: #fff;
    }

    .u-tab-item-active::before {
        content: "";
        position: absolute;
        border-left: 4px solid #FF7250;
        height: 32rpx;
        left: 0;
        top: 39rpx;
    }

    .u-tab-view {
        height: 100%;
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

    // 微信小程序不支持组件添加骨架屏 弄个假的兼容
    .camouflage-skeleton {
        width: 100%;

        .items {
            display: flex;
            align-items: center;
            margin-top: 20rpx;
            margin-left: 20rpx;

            .image-box {
                width: 186rpx;
                height: 186rpx;
                margin-right: 16rpx;
            }

            .value {
                .item {
                    width: 312rpx;
                    height: 50rpx;
                    font-size: 28upx;
                    font-weight: bold;
                    margin-top: 10rpx;
                }
            }
        }
    }


    .thumb-box {
        display: flex;
        align-items: center;
        margin-top: 20rpx;
    }

    .right-box {
        background-color: rgb(255, 255, 255);
        margin-bottom: 100rpx;

        .item-title-text {
            font-size: 26rpx;
            color: $u-main-color;
            font-weight: bold;
            margin: 24rpx 0 0 24rpx;
        }
    }

    .page-view {
        padding: 16rpx;
    }

    .class-item {
        margin-bottom: 30rpx;
        background-color: #fff;
        padding: 16rpx;
        border-radius: 8rpx;
    }

    .item-title {
        font-size: 26rpx;
        color: $u-main-color;
        font-weight: bold;
    }

    .item-menu-name {
        font-weight: normal;
        font-size: 24rpx;
        color: $u-main-color;
    }

    .foods-image-box {
        width: 186rpx;
        height: 186rpx;
    }

    .item-menu-box {
        margin-left: 16rpx;

        view {
            margin-top: 10rpx;

            &:frist-child {
                margin-top: 0;
            }
        }

        .item-menu-name {
            font-size: 28upx;
            font-weight: bold;
        }

        .item-menu-dosing {
            width: 312rpx;
            font-size: 24upx;
            color: #777777;
            display: block;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .item-menu-sales {
            font-size: 22upx;
            color: #AAAAAA;
        }

        .item-menu-price {
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

    .not-data {
        width: 100rpx;
        text-align: center;

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
