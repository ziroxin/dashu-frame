<template>
	<!-- 余额明细 -->
	<view class="container">
		<view class="tabs">
			<view class="radio-tabs">
				<view class="date-item" v-for="(item,index) in radioList" :key="index"
					:class="{'active':currentDate == index}" @click="chooseDate(item.time,index)">
					{{item.text}}
				</view>
			</view>
			<view class="checkbox-tabs" @click="chooseDate('','3')">
				<view class="date-item" v-show="checkboxTab">
					{{checkboxTab}}
				</view>
				<image src="@/static/images/my/date.png" mode="widthFix"></image>
			</view>
		</view>
		<!-- <mescroll-body ref="mescrollRef" @down="downCallback" @up="upCallback"> -->
		<block v-if="balanceList.length>0">
			<view class="balance-record">
				<view class="item" v-for="(item,index) in balanceList" :key="index">
					<view class="change-type">
						<view class="label">
							余额充值
						</view>
						<view class="date">
							2021-4-20 11:00
						</view>
					</view>
					<view class="price" :class="{'up':true}">
						+90.00
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
		<!-- </mescroll-body> -->
		<u-calendar v-model="show" mode="date" @change="changeDate"></u-calendar>
		<u-loadmore :status="status" />
	</view>
</template>

<script>
	import MescrollMixin from "@/uni_modules/mescroll-uni/components/mescroll-uni/mescroll-mixins.js";
	export default {
		mixins: [MescrollMixin],
		data() {
			return {
				show: false, //日历组件展示
				radioList: [{
						time: new Date(),
						text: '今日'
					},
					{
						time: new Date(new Date().getTime() - (60 * 60 * 24 * 1000)),
						text: '昨日'
					},
				],
				checkboxTab: '',
				// 选中日期
				currentDate: 0,
				balanceList: [],
				status: 'loadmore',
				page: {
					num: 1
				},
				total: 0,
			}
		},
		onReachBottom() {
			if (this.balanceList.length >= this.total) return;
			this.status = 'loading';
			this.page['num'] += 1;
			this.getBalanceDetails(this.page);
		},
		onLoad() {

		},
		onShow() {
			this.page['num'] = 1;
			this.getBalanceDetails(this.page);
		},
		methods: {
			getBalanceDetails(page) {
				let _that = this,oldList = [..._that.balanceList];
				let type = this.currentDate + 1;
				_that.$http('/api/user/capital_flow', 'POST', {
						type: type,
						shop_id: this.$store.state.shopId,
						month: this.checkboxTab,
						pageSize:10,
						page:page.num
					})
					.then(data => {
						if (page.num == 1) {
							oldList = []
						}
						_that.total = data.data.data.total;
						_that.balanceList = data.data.data.data;
						if (_that.balanceList.length >= data.data.data.total) {
							_that.status = 'nomore';
						}
					})
			},
			// downCallback(mescroll) {
			// 	mescroll.resetUpScroll();
			// },
			// async upCallback(page) {
			// 	let _that = this,
			// 		oldList = [..._that.balanceList],
			// 		newList = [];
			// 	let balanceList = await _that.getBalanceDetails();
			// 	balanceList.then(data => {
			// 		if (page.num == 1) {
			// 			oldList = []
			// 		}
			// 		_that.balanceList = oldList.concat(data.data.data.data)
			// 		_that.mescroll.endBySize(10, data.data.data.total);
			// 		setTimeout(() => {
			// 			this.$nextTick(function() {
			// 				this.loading = false;
			// 			})
			// 		}, 1000)
			// 	})
			// },
			// 日历组件确定事件
			changeDate(e) {
				this.checkboxTab = e.result;
				this.getBalanceDetails()
			},
			chooseDate(date, index) {
				this.currentDate = index;
				if (index == 3) {
					this.show = !this.show;
				} else {
					this.checkboxTab = ""
				}
				this.getBalanceDetails()
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		width: 100%;
		padding: 24rpx;
		box-sizing: border-box;
	}

	.tabs {
		display: flex;
		align-items: center;
		justify-content: space-between;

		.radio-tabs {
			display: flex;
			align-items: center;
			font-size: 30upx;
			color: #777777;

			.date-item {
				margin-right: 50rpx;

				&.active {
					font-size: 44upx;
					color: #FF7250;
					font-weight: bold;
				}
			}
		}

		.checkbox-tabs {
			height: 42rpx;
			flex-shrink: 0;
			display: flex;
			align-items: center;

			.date-item {
				font-size: 34upx;
				font-weight: bold;
				color: #FF7250;
			}

			image {
				width: 42rpx;
				height: 42rpx;
				margin-left: 20rpx;
			}
		}
	}

	.balance-record {
		.item {
			width: 100%;
			height: 142rpx;
			display: flex;
			justify-content: space-between;
			align-items: center;
			border-bottom: 1px solid #EEEEEE;

			.change-type {

				.label {
					font-size: 30upx;
					color: #333333;
					font-weight: bold;
				}

				.date {
					margin-top: 16rpx;
					font-size: 26upx;
					color: #777777;
				}
			}

			.price {
				font-size: 36upx;
				color: #AAAAAA;
				font-weight: bold;

				&.up {
					color: #FF7250;
				}
			}
		}
	}
</style>
