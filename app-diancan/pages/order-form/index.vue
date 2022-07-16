<template>
    <!-- 订单 -->
    <view class="container u-skeleton">
        <u-sticky :enable='enable'>
            <view class="tabs-box">
                <u-tabs-swiper ref="uTabs" height="100" :list="list" :current="current" @change="tabsChange"
                    :is-scroll="false" font-size="32" active-color="#FFFFFF" inactive-color="rgba(255,255,255,0.65)"
                    bg-color="#FF7250">
                </u-tabs-swiper>
            </view>
        </u-sticky>

        <mescroll-body ref="mescrollRef" class="data-list-box" @init="mescrollInit" @up="upCallback" :down="downOption"
            @down="downCallback">
            <!-- #ifdef MP-WEIXIN -->
            <view class="camouflage-skeleton" v-if="loading">
                <view class="items" v-for="item in 3" :key="item">
                    <view class="item u-skeleton-fillet"></view>
                    <view class="item-box">
                        <view class="image-box u-skeleton-fillet"></view>
                        <view class="item-boxs">
                            <view class="item u-skeleton-fillet"></view>
                            <view class="item u-skeleton-fillet"></view>
                        </view>
                    </view>
                </view>
            </view>
            <!-- #endif -->
            <block v-if="orderList.length>0">
                <view class="box" v-for="(item,index) in orderList" :key="item.id" @click="JumpPage('/pages/order-form/order-details', {
						orderId: item.id
					})">
                    <order-detail v-if="item" :orderInfo="item" :payStatus="item.status" @cancel="cancel">
                    </order-detail>
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

        </mescroll-body>
        <u-skeleton :loading="loading" :animation="true" bgColor="#FFF"></u-skeleton>
        <u-modal v-model="cancelVal" :title="modelTitle" :content="modelContent" confirm-text="确认" cancel-text="取消"
            show-cancel-button @confirm="confirm(cancelText)"></u-modal>
        <u-modal v-model="oauthShow" title="用户授权" content="用户未授权,是否去授权？" confirm-text="去授权" cancel-text="取消"
            show-cancel-button @confirm="goOauth"></u-modal>
        <u-loadmore :status="status" />
    </view>
</template>

<script>
    import {
        mapState
    } from 'vuex'
    import orderDetail from '@/components/order-detail/index.vue';
    import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
    export default {
        mixins: [MescrollMixin],
        components: {
            orderDetail,
        },
        data() {
            return {
                oauthShow: false,
                cancelVal: false,
                loading: true,
                downOption: {
                    auto: false,
                },
                list: [{
                        name: '全部'
                    },
                    {
                        name: '待付款'
                    },
                    {
                        name: '待取餐'
                    },
                    {
                        name: '退款中'
                    },
                    {
                        name: '已完成',
                    }
                ],
                current: 0, // tabs组件的current值，表示当前活动的tab选项
                swiperCurrent: 0, // swiper组件的current值，表示当前那个swiper-item是活动的
                enable: false,
                modelTitle: '',
                modelContent: '',
                cancelText: '',
                orderId: '',
                status: 'loadmore',
                page: {
                    num: 1
                },
                total: 0,
            }
        },
        onReachBottom() {
            console.log("on reach bottom");
            if (this.orderList.length >= this.total) return;
            this.status = 'loading';
            this.page['num'] += 1;
            this.getOrderList(this.page);
        },
        computed: {
            ...mapState(['selectedShop', 'orderList'])
        },
        onLoad() {
            // #ifdef MP-WEIXIN
            if (!uni.getStorageSync('wxLoginCode')) {
                console.log('下单页授权')
                let info = this.$oauth(e)
                if (info.loginCode) {
                    uni.setStorageSync('wxLoginCode', info.loginCode)
                }
            }
            // #endif
        },
        onShow() {
            this.enable = true;
            this.page['num'] = 1
            this.getOrderList(this.page);
        },
        onHide() {
            this.enable = false;
        },
        onReady() {
            // this.isOAuth();
        },
        methods: {
            getOrderList(page) {
                console.log(page, "getOrderList");
                let _that = this
                let oldList = [..._that.orderList]
                let newList = []
                let judge = typeof this.$store.state.oauthInfo === 'string' ?
                    this.$store.state.oauthInfo.indexOf('loginCode') :
                    this.$store.state.oauthInfo.hasOwnProperty('loginCode')
                let status = _that.swiperCurrent == 0 ? 99 : _that.swiperCurrent == 1 ? 0 : _that.swiperCurrent == 2 ?
                    1 : _that.swiperCurrent == 3 ? -1 : 55
                _that.$http('/api/order/order_list', 'POST', {
                    shop_id: _that.$store.state.shopId,
                    status: status,
                    pageSize: 10,
                    page: page.num
                }).then(data => {
                    console.log(data);
                    if (page.num == 1) {
                        oldList = []
                    }
                    newList = oldList.concat(data.data.data.data)
                    if (newList.length >= data.data.data.total) {
                        _that.status = 'nomore';
                    }
                    if (judge == 0 || judge) {
                        _that.$store.commit('SET_ORDER_LIST', newList)
                    } else {
                        _that.$store.commit('SET_ORDER_LIST', [])
                    }
                    this.total = data.data.data.total;
                    _that.mescroll.endBySize(10, data.data.data.total);
                    setTimeout(() => {
                        this.$nextTick(function() {
                            this.loading = false;
                        })
                    }, 1000)
                }).catch((err) => {
                    console.log(err, "errorziro");
                    setTimeout(() => {
                        this.$nextTick(function() {
                            this.loading = false;
                        })
                    }, 1000)
                })
            },
            isOAuth() {
                if (!this.$store.state.oauthInfo?.loginCode) {
                    this.oauthShow = !this.oauthShow
                }
            },
            async goOauth(e) {
                this.oauthShow = false
                let _that = this;
                // #ifdef MP-ALIPAY
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
                // #endif
                // #ifdef MP-WEIXIN

                if (uni.getUserProfile) {
                    uni.getUserProfile({
                        desc: '获取你的昵称、头像、地区及性别',
                        lang: 'zh_CN',
                        success: (res) => {
                            if (res.userInfo) {
                                _that.$http('/api/wx_login/wxlogin', 'POST', {
                                        code: uni.getStorageSync('wxLoginCode'),
                                        wx_name: res.userInfo.nickName,
                                        avatar: res.userInfo.avatarUrl,
                                        gender: res.userInfo.gender
                                    })
                                    .then(data => {
                                        _that.$store.commit('SET_TOKEN', data.data.data.userinfo
                                            .token);
                                        _that.$http('/api/user/userInfo', 'POST')
                                            .then(res => {
                                                _that.$message(data.data.msg)
                                                _that.$store.commit('SET_OAUTH_INFO', {
                                                    loginCode: uni.getStorageSync(
                                                        'wxLoginCode')
                                                });
                                            })

                                    }).catch((err) => {
                                        console.log(JSON.stringify(err))
                                    })
                            }
                        },
                        fail: (err) => {
                            console.log(err)
                            this.$message('用户取消授权')
                        }
                    })
                } else {
                    console.log('wx')
                    wx.getUserProfile({
                        desc: '获取你的昵称、头像、地区及性别',
                        lang: 'zh_CN',
                        success: (res) => {
                            if (res.userInfo) {
                                _that.$http('/api/wx_login/wxlogin', 'POST', {
                                        code: uni.getStorageSync('wxLoginCode'),
                                        wx_name: res.userInfo.nickName,
                                        avatar: res.userInfo.avatarUrl,
                                        gender: res.userInfo.gender
                                    })
                                    .then(data => {
                                        _that.$store.commit('SET_TOKEN', data.data.data.userinfo
                                            .token);
                                        _that.$http('/api/user/userInfo', 'POST')
                                            .then(res => {
                                                _that.$message(data.data.msg)
                                                _that.$store.commit('SET_OAUTH_INFO', {
                                                    loginCode: uni.getStorageSync(
                                                        'wxLoginCode')
                                                });
                                            })
                                    }).catch((err) => {
                                        console.log(JSON.stringify(err))
                                    })
                            }
                        },
                        fail: (err) => {
                            console.log(err)
                            this.$message('用户取消授权')
                        }
                    })
                }

                // #endif
            },
            confirm(cancelText) {
                let apiAddr = '',
                    _that = this;

                uni.showToast({
                    title: '社区版暂不支持',
                    content: '需要支付功能，请联系作者购买商业版',
                    duration: 5000
                });
            },
            cancel(obj) {
                this.cancelVal = !this.cancelVal;
                this.modelTitle = obj.cancelText;
                this.modelContent = '是否' + obj.cancelText + '？';
                this.cancelText = obj.cancelText
                this.orderId = obj.orderId
            },
            // tabs通知swiper切换
            tabsChange(index) {
                this.swiperCurrent = index;
                this.current = index;
                this.getOrderList(this.page);
            },
            // swiper-item左右移动，通知tabs的滑块跟随移动
            transition(e) {
                let dx = e.detail.dx;
                this.$refs.uTabs.setDx(dx);
            },
            // 由于swiper的内部机制问题，快速切换swiper不会触发dx的连续变化，需要在结束时重置状态
            // swiper滑动结束，分别设置tabs和swiper的状态
            animationfinish(e) {
                let current = e.detail.current;
                this.$refs.uTabs.setFinishCurrent(current);
                this.swiperCurrent = current;
                this.current = current;
            },

            // mescrollInit(mescroll) {
            // 	this.mescroll = mescroll;
            // },
            downCallback(mescroll) {

                this.page['num'] = 1
                this.getOrderList(this.page);
                // this.$nextTick(function() {
                // 	this.mescroll.resetUpScroll();
                // })
            },
            // // TODO
            // upCallback(page) {
            // 	// console.log(page.num,this.orderList)
            // 	let _that = this,
            // 		oldList = [..._that.orderList],
            // 		newList = [],
            // 		judge = typeof this.$store.state.oauthInfo === 'string' ? this.$store.state.oauthInfo.indexOf(
            // 			'loginCode') : this.$store.state.oauthInfo.hasOwnProperty('loginCode'),
            // 		status = _that.swiperCurrent == 0 ? 99 : _that.swiperCurrent == 1 ? 0 : _that.swiperCurrent == 2 ?
            // 		1 : _that.swiperCurrent == 3 ? -1 : 55;
            // 	_that.$http('/api/order/order_list', 'POST', {
            // 		shop_id: _that.$store.state.shopId,
            // 		status: status,
            // 		pageSize: 4,
            // 		page: page.num
            // 	}).then(data => {
            // 		if (data.data.code == 1) {
            // 			if (page.num == 1) {
            // 				oldList = []
            // 			}
            // 			newList = oldList.concat(data.data.data.data)
            // 			if (judge == 0 || judge) {
            // 				_that.$store.commit('SET_ORDER_LIST', newList)
            // 			} else {
            // 				_that.$store.commit('SET_ORDER_LIST', [])
            // 			}
            // 			_that.mescroll.endBySize(4, data.data.data.total);
            // 			setTimeout(() => {
            // 				this.$nextTick(function() {
            // 					this.loading = false;
            // 				})
            // 			}, 1000)
            // 		} else {
            // 			setTimeout(() => {
            // 				this.$nextTick(function() {
            // 					this.loading = false;
            // 				})
            // 			}, 1000)
            // 		}
            // 	}).catch(() => {
            // 		setTimeout(() => {
            // 			this.$nextTick(function() {
            // 				this.loading = false;
            // 			})
            // 		}, 1000)
            // 	})
            // },

        }
    }
</script>

<style lang="scss" scoped>
    .con-box {
        background-color: #F8F8F8;
        height: calc(100vh - 44px - 100rpx);
    }

    .tabs-box {
        width: 100%;
    }

    .data-list-box {
        height: auto;
        overflow: auto;
    }

    /* #ifdef MP-WEIXIN */
    // 微信小程序不支持组件添加骨架屏 弄个假的兼容
    .camouflage-skeleton {
        width: 100%;
        padding: 0 24rpx;
        box-sizing: border-box;

        .items {

            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-left: 20rpx;
            padding: 0 24rpx;
            box-sizing: border-box;

            .item-box {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-top: 40rpx;

                .image-box {
                    width: 180rpx;
                    height: 180rpx;
                    margin-right: 40rpx;
                    flex-shrink: 0;
                }

                .item-boxs {
                    width: 100%;
                    flex-shrink: 0;

                    .item {
                        width: 380rpx;
                    }
                }
            }

            .item {
                width: 100%;
                height: 50rpx;
                font-size: 28upx;
                font-weight: bold;
                margin-top: 30rpx;
            }
        }
    }

    /* #endif */
</style>
