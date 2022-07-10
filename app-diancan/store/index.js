//引用Vuex
import Vue from 'vue'
import Vuex from 'vuex'
import request from '@/common/request.js';
var http = request.mainRequest;
Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: uni.getStorageSync('token') || '',
        //商品数据
        homeGoodsList: [{
                "title": "豆角焖面",
                "dosing": "焖面、青椒、豆角、朝天椒",
                "img": '',
                "sales": 2390,
                "price_selling": 16,
                "quantity": 0
            },
            {
                "title": "豆角焖面",
                "dosing": "焖面、青椒、豆角、朝天椒",
                "img": '',
                "sales": 2390,
                "price_selling": 16,
                "quantity": 0
            },
            {
                "title": "豆角焖面",
                "dosing": "焖面、青椒、豆角、朝天椒",
                "img": '',
                "sales": 2390,
                "price_selling": 16,
                "quantity": 0
            }
        ],
        goodsList: [],
        selectedShop: [], //已选商品
        orderList: [], // 订单列表
        searchList: [], //搜索列表
        oauthInfo: uni.getStorageSync('oauthInfo') || '',
        latitudeAndLongitude: uni.getStorageSync('latitudeAndLongitude') || '',
        merchantId: uni.getStorageSync('merchantId') || '',
        merchantInfo: uni.getStorageSync('merchantInfo') || '',
        shopId: uni.getStorageSync('shopId') || '',
        shopTotalPrice: 0,
        shopQuantity: 0,
        openId: uni.getStorageSync('openId') || '',
    },
    mutations: {
        SET_SHOP_ID: (state, shopId) => {
            state.shopId = shopId;
            uni.setStorageSync('shopId', shopId)
        },
        SET_MERCHANT_INFO: (state, merchantInfo) => {
            state.merchantInfo = merchantInfo;
        },
        SET_MERCHANT_ID: (state, merchantId) => {
            state.merchantId = merchantId;
            uni.setStorageSync('merchantId', merchantId)
        },
        SET_LATANDLON: (state, latAndLon) => {
            state.latitudeAndLongitude = latAndLon;
            uni.setStorageSync('latitudeAndLongitude', latAndLon);
        },
        SET_TOKEN: (state, token) => {
            state.token = token;
            uni.setStorageSync('token', token)
        },
        // 获取授权
        SET_OAUTH_INFO: (state, oauthInfo) => {
            state.oauthInfo = oauthInfo
            uni.setStorageSync('oauthInfo', oauthInfo)
        },
        // 选择商品（添加/减少）
        CHANGES_SHOP: (state, shop) => {
            state.selectedShop = shop
        },
        // 清空已选商品
        EMPTY_SELECT_SHOP: (state, isEmpty) => {
            state.selectedShop = [];
        },
        EMPTY_SEARCH_LIST: (state, isEmpty) => {
            state.searchList = [];
        },
        // 设置商品列表
        SET_GOODS_LIST: (state, goodsList) => {
            state.goodsList = goodsList;
        },
        SET_HOME_GOODS_LIST: (state, goodsList) => {
            state.homeGoodsList = goodsList;
        },
        // 设置订单列表
        SET_ORDER_LIST: (state, orderList) => {
            state.orderList = orderList
        },
        SHOP_QUANTITY: (state, shopQuantity) => {
            state.shopQuantity = shopQuantity
        },
        SHOP_TOTAL_PRICE: (state, shopTotalPrice) => {
            state.shopTotalPrice = (shopTotalPrice + 0)
        },
        SET_SEARCH_LIST: (state, searchList) => {
            state.searchList = searchList;
        },
        SET_OPEN_ID: (state, openId) => {
            state.openId = openId;
            uni.setStorageSync('openId', openId);
        }
    },
    actions: {
        // 获取首页推荐商品列表
        async getHomeGoodsList({
            commit,
            state
        }, goodsdata) {
            goodsdata.forEach((item, index) => {
                if (state.selectedShop.length > 0) {
                    state.selectedShop.forEach((acShop, index) => {
                        if (item.id == acShop.id) {
                            Object.assign(item, acShop);
                        } else {
                            item['quantity'] = 0;
                            item['onceTotalPrice'] = 0;
                        }
                    })
                } else {
                    item['quantity'] = 0;
                    item['onceTotalPrice'] = 0;
                }
            })
            commit('SET_HOME_GOODS_LIST', goodsdata)
        },
        // 获取商品列表
        async getGoodsList({
            commit,
            state
        }, goodsdata) {
            let goodsData = await http('/can/api/open/dishes/list', 'GET', {
                shopId: state.shopId
            })
            let goodsList = goodsData.data.data;

            goodsList.forEach((item, index) => {
                item.dishes.forEach((items, idx) => {
                    items['quantity'] = 0;
                    items['onceTotalPrice'] = 0;
                    state.selectedShop.forEach((acShop, index) => {
                        if (items.dishesId == acShop.dishesId) {
                            Object.assign(items, acShop);
                        }
                    })
                })
            })
            commit('SET_GOODS_LIST', goodsList)
        },
        // 搜索商品
        async getSearchList({
            commit,
            state
        }, goodsdata) {
            if (state.searchList.length <= 0) {
                goodsdata.forEach((item, index) => {
                    if (!item.hasOwnProperty('quantity')) {
                        item['quantity'] = 0;
                        item['onceTotalPrice'] = 0;
                    }
                })
            } else {
                goodsdata.forEach((item, index) => {
                    state.selectedShop.forEach((acShop, index) => {
                        if (item.dishesId == acShop.dishesId) {
                            Object.assign(item, acShop);
                        } else {
                            item['quantity'] = 0;
                            item['onceTotalPrice'] = 0;
                        }
                    })
                })
            }

            commit('SET_SEARCH_LIST', goodsdata)
        },
        // 更新商品信息(增加减少选中数量)
        updateShopDetails({
            commit,
            state
        }, shopInfo) {
            let selectedShop = [...state.selectedShop];
            let index = selectedShop.findIndex(item => {
                return item.dishesId == shopInfo.item.dishesId;
            })
            if (shopInfo.status) {
                // 添加选中商品
                if (selectedShop[index] && selectedShop[index]['quantity']) {
                    shopInfo.item['quantity'] = selectedShop[index]['quantity'];
                }
                shopInfo.item['quantity'] ? shopInfo.item['quantity'] += 1 : shopInfo.item['quantity'] = 1;
                shopInfo.item['onceTotalPrice'] = shopInfo.item['quantity'] * shopInfo.item['currentPrice'];
                if (index == -1) {
                    selectedShop.push(shopInfo.item);
                    if (selectedShop[index] && selectedShop[index]['quantity']) {
                        selectedShop[index]['quantity'] = shopInfo.item['quantity'];
                    }
                } else {
                    selectedShop[index]['quantity'] = shopInfo.item['quantity'];
                    selectedShop[index]['onceTotalPrice'] = shopInfo.item['onceTotalPrice'];
                }

            } else {
                // 减少选中商品
                shopInfo.item['quantity'] ? shopInfo.item['quantity'] -= 1 : shopInfo.item['quantity'] = 0;
                shopInfo.item['onceTotalPrice'] = shopInfo.item['quantity'] * shopInfo.item['currentPrice'];
                if (shopInfo.item['quantity']) {
                    selectedShop[index]['quantity'] = shopInfo.item['quantity'];
                    selectedShop[index]['onceTotalPrice'] = shopInfo.item['onceTotalPrice'];
                } else {
                    if (index != -1) {
                        selectedShop.splice(index, 1)
                    }
                }
            }
            commit('CHANGES_SHOP', selectedShop);

            state.goodsList.forEach((item, index) => {
                item.dishes.forEach((items, idx) => {
                    if (items.dishesId == shopInfo.item.dishesId) {
                        Object.assign(items, shopInfo.item);
                    }
                })
            })

        },
        // 
        async changeOrderListData({
            commit,
            state
        }, order) {
            var orderList = [order, ...state.orderList];
            // 把已选商品quantity置为0
            state.goodsList.forEach((item, index) => {
                item.dishes.forEach((items, indexs) => {
                    items['quantity'] = 0;
                    items['onceTotalPrice'] = 0;
                })
            })
            state.homeGoodsList.forEach((item, index) => {
                item['quantity'] = 0;
                item['onceTotalPrice'] = 0;
            })
            // 清空已选商品列表
            commit('EMPTY_SELECT_SHOP');
            // 清空搜索商品列表
            commit('EMPTY_SEARCH_LIST');
        },

    },
    getters: {
        // 获取当前选中餐品的数量
        selectedShopQuantity: (state, getters) => {
            return state.selectedShop.reduce((acc, cur) => {
                return acc += cur.quantity
            }, 0);
        },
        // 获取当前选中餐品的总价格
        selectedShopTotalPrice: (state, getters) => {
            let shopTotalPrice = state.selectedShop.reduce((acc, cur) => {
                return acc += cur.onceTotalPrice
            }, 0);
            return shopTotalPrice
        },

    }
})
