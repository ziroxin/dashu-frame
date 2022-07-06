<template>
  <div class="app-container">
    <!--    操作按钮-->
    <div style="margin-bottom: 10px">
      <el-button type="primary" icon="el-icon-plus" @click="orderAdd">添加</el-button>
      <el-button type="info" icon="el-icon-edit" @click="orderUpdate">修改</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="orderDelete">删除</el-button>
      <el-date-picker v-model="value" style="margin: 10px" type="daterange" align="right"
                      unlink-panels range-separator="至" start-placeholder="开始日期"
                      end-placeholder="结束日期" :picker-options="pickerOptions"
      />
      <el-button type="primary" icon="el-icon-plus" @click="getshopListByTime">按时间查询</el-button>

    </div>

    <!--    表格部分-->
    <el-table :data="orderData" row-key="orderId" style="margin-bottom: 20px;" border
              @selection-change="selectionChangeHandlerOrder"
    >
      <el-table-column type="selection" width="50" header-align="center" align="center" />
      <el-table-column prop="tableName" label="餐桌名称" sortable />
      <el-table-column prop="diningNumber" label="就餐人数" sortable />
      <el-table-column prop="diningTime" label="就餐时间" sortable />
      <el-table-column prop="diningAmount" label="就餐金额" sortable />
      <el-table-column prop="invoice" label="是否开发票" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.invoice === '0'">已开票</span>
          <span v-else-if="scope.row.invoice === '1'">未开发票</span>
        </template>
      </el-table-column>
      <el-table-column prop="orderComplete" label="订单是否完成" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.orderComplete === '0'">订单已完成</span>
          <span v-else-if="scope.row.orderComplete === '1'">订单未完成</span>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="210" header-align="center" align="center">
        <template slot-scope="{row}">
          <el-button type="text" @click="getOrderDetailsList(row)">订单详情</el-button>
          <el-button type="text" @click="Invoice(row)">开发票</el-button>
          <el-button type="text" @click="completeOrder(row)">完成订单</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--    添加修改数据弹窗-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="orderDataForm" :model="temp" :rules="rules" label-position="right" label-width="150px"
               style="width: 500px; margin-left: 50px;"
      >

        <el-form-item label="选择餐桌" prop="tableId">
          <el-select v-model="temp.tableId" class="filter-item" placeholder="请选择餐桌" @change="changeTableName">
            <el-option v-for="item in tableNameOptions" :key="item.tableId" :label="item.tableName"
                       :value="item.tableId"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="就餐人数" prop="diningNumber">
          <el-input v-model="temp.diningNumber" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitJudgment">保存</el-button>
        <el-button @click="dialogFormVisible=false">取消</el-button>
      </div>
    </el-dialog>

    <!--    订单详情弹窗-->
    <el-dialog title="订单详情" :visible.sync="dialogFormOrder">
      <el-table :data="orderDeletailData" row-key="orderDeletailId" style="margin-bottom: 20px;" border>
        <el-table-column prop="dishesName" label="菜品名称" sortable />
        <el-table-column prop="dishesNumber" label="菜品数量" sortable />
        <el-table-column prop="dishesNumber" label="菜品数量" sortable />
        <el-table-column prop="dishesAmount" label="金额" sortable />
      </el-table>
    </el-dialog>

    <!--    发票详情弹窗-->
    <el-dialog title="发票详情" :visible.sync="dialogFormBill" width="60%">
      <el-form ref="orderBillDataForm" :model="orderBillData" :rules="rules" label-position="right" label-width="150px"
               style="width: 500px; margin-left: 50px;"
      >

        <el-form-item label="单位名称：" prop="companyName" :inline="true">
          <el-input v-model="orderBillData.companyName" />
          <el-button class="tag-read" type="primary" :data-clipboard-text="orderBillData.companyName" @click="copy">复制
          </el-button>
        </el-form-item>

        <el-form-item label="纳税人识别号：" prop="taxId">
          <el-input v-model="orderBillData.taxId" />
          <el-button class="tag-read" type="primary" :data-clipboard-text="orderBillData.taxId" @click="copy">复制
          </el-button>
        </el-form-item>

        <el-form-item label="单位地址：" prop="companyAdress">
          <el-input v-model="orderBillData.companyAdress" />
          <el-button class="tag-read" type="primary" :data-clipboard-text="orderBillData.companyAdress" @click="copy">
            复制
          </el-button>
        </el-form-item>

        <el-form-item label="单位联系电话：" prop="companyPhone">
          <el-input v-model="orderBillData.companyPhone" />
          <el-button class="tag-read" type="primary" :data-clipboard-text="orderBillData.companyPhone" @click="copy">
            复制
          </el-button>
        </el-form-item>

        <el-form-item label="开户行：" prop="bank">
          <el-input v-model="orderBillData.bank" />
          <el-button class="tag-read" type="primary" :data-clipboard-text="orderBillData.bank" @click="copy">复制
          </el-button>
        </el-form-item>

        <el-form-item label="银行账号：" prop="bankAccount">
          <el-input v-model="orderBillData.bankAccount" />
          <el-button class="tag-read" type="primary" :data-clipboard-text="orderBillData.bankAccount" @click="copy">复制
          </el-button>
        </el-form-item>

        <el-form-item label="开票金额：" prop="invoiceAmount">
          <el-input v-model="orderBillData.invoiceAmount" />
          <el-button class="tag-read" type="primary" :data-clipboard-text="orderBillData.invoiceAmount" @click="copy">
            复制
          </el-button>
        </el-form-item>

      </el-form>
    </el-dialog>

  </div>
</template>

<script>

import {orderAdd, orderDelete, orderList, orderUpdate} from '@/api/order';
import {tableList} from '@/api/table';
import {orderDeletailsList} from '@/api/order-deletail';
import {orderBillById} from '@/api/order-bill';
import Clipboard from 'clipboard';
import moment from 'moment';

const startYear = new Date(
  moment().year(moment().year()).startOf('year').format('YYYY-MM-DD')
);
const startMonth = new Date(
  moment().month(moment().month()).startOf('month').format('YYYY-MM-DD')
);
const startWeeK = new Date(
  moment().week(moment().week()).startOf('week').format('YYYY-MM-DD')
);
const newDay = new Date();

export default {
  name: 'Index',
  data() {
    return {
      //表格数据
      orderData: [],
      //时间数据
      value: [],
      time: {startTime: '', endTime: ''},
      //订单详情数据
      orderDeletailData: [],
      //发票详情的数据
      orderBillData: {},
      //勾选的数据
      changeData: [],
      //分页
      pager: {page: 1, limit: 10},
      totalCount: 0,
      //  每一条数据
      temp: {},
      // 对话框属性
      textMap: {update: '修改', create: '新增'},
      dialogStatus: '',
      dialogFormVisible: false,
      dialogFormOrder: false,
      dialogFormBill: false,
      //查询的餐桌数据
      tableNameOptions: [],
      //删除数据的ID数据
      orderIds: [],
      //表单规则
      rules: {tableName: [{required: true, message: '餐桌不能为空', trigger: 'blur'}]},
      // 日期扩展
      pickerOptions: {
        shortcuts: [
          {
            text: '今日',
            onClick(picker) {
              picker.$emit('pick', [newDay, newDay]);
            }
          },
          {
            text: '本周',
            onClick(picker) {
              picker.$emit('pick', [startWeeK, newDay]);
            }
          },
          {
            text: '本月',
            onClick(picker) {
              picker.$emit('pick', [startMonth, newDay]);
            }
          },
          {
            text: '全年',
            onClick(picker) {
              picker.$emit('pick', [startYear, newDay]);
            }
          }
        ]
      }
    }
  },
  created() {
    this.getOrderList()
    this.getTableList()
  },
  methods: {
    // 表格勾选
    selectionChangeHandlerOrder(val) {
      this.changeData = val
    },
    //分页
    handleCurrentChange(page) {
      this.pager.page = page
    },
    //查询订单信息
    getOrderList() {
      orderList(this.time).then(response => {
        this.orderData = response.data
        this.totalCount = response.data.length
      })
    },
    //查询餐桌信息
    getTableList() {
      tableList().then(response => {
        this.tableNameOptions = response.data
      })
    },
    changeTableName(id) {
      // this.title 是通过选中的id去从标题列表里面查找得到的是选中的那一条数据的一个对象，包含选中的数据的 name、id 等 ， 后面可以通过 this.title.name 来获取标题名称
      const tablelist = this.tableNameOptions.find(item => item.tableId === id)
      this.temp.tableName = tablelist.tableName
      this.temp.shopId = tablelist.shopId
    },
    resetTemp() {
      this.temp = {
        shopId: '',
        tableId: '',
        tableName: '',
        diningNumber: 3,
        invoice: '1',
        orderComplete: '1',
        startTime: '',
        endTime: ''
      }
    },
    //  点击添加按钮后
    orderAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['orderDataForm'].clearValidate()
      })
    },
    //点击修改按钮后
    orderUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行修改！'})
      } else if (this.changeData.length > 1) {
        this.$message({type: 'warning', message: '修改时仅允许选择一条数据！'})
      } else {
        this.temp = Object.assign({}, this.changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['orderDataForm'].clearValidate()
        })
      }
    },
    //  提交数据
    submitJudgment() {
      this.$refs['orderDataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'update') {
            orderUpdate(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '修改成功！'})
                this.dialogFormVisible = false
                this.getOrderList()
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            orderAdd(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '添加成功！'})
                this.dialogFormVisible = false
                this.getOrderList()
              } else {
                this.$message({type: 'error', message: '添加失败！'})
              }
            })
          }
        }
      })
    },
    //删除订单
    orderDelete() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行删除！'})
      } else {
        this.$confirm('确定要删除吗？', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          this.orderIds.push(...this.changeData.map(r => r.orderId))
          orderDelete(this.orderIds).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '删除成功！'})
              this.getOrderList()
            } else {
              this.$message({type: 'error', message: '删除失败！'})
            }
          })
        })
      }
    },
    //订单详情
    getOrderDetailsList(row) {
      const orderId = row.orderId
      orderDeletailsList(orderId).then(response => {
        this.orderDeletailData = response.data
      })
      this.dialogFormOrder = true
    },
    //  完成订单
    completeOrder(row) {
      this.temp = Object.assign({}, row)
      if (this.temp.orderComplete === '0') {
        this.$message({type: 'warning', message: '该订单已完成！'})
      } else {
        this.$confirm('此订单确定已结账了吗？', '结账提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          this.temp.orderComplete = '0'
          orderUpdate(this.temp).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '订单已完成！'})
              this.getOrderList()
            } else {
              this.$message({type: 'error', message: '完成订单失败！'})
            }
          })
        })
      }
    },
    //  开发票
    Invoice(row) {
      const orderId = row.orderId
      orderBillById(orderId).then(response => {
        this.orderBillData = Object.assign({}, response.data)
      })
      this.dialogFormBill = true
      this.$nextTick(() => {
        this.$refs['orderBillDataForm'].clearValidate()
      })
    },
    //复制文本
    copy() {
      var clipboard = new Clipboard('.tag-read')
      clipboard.on('success', e => {
        console.log('复制成功')
        // 释放内存
        // clipboard.destroy()
      })
      clipboard.on('error', e => {
        // 不支持复制
        console.log('该浏览器不支持复制')
        // 释放内存
        clipboard.destroy()
      })
    },
    //根据时间查询订单信息
    getshopListByTime() {
      let yy = this.value[0].getFullYear();
      let mm = this.value[0].getMonth() + 1;
      let dd = this.value[0].getDate();
      this.time.startTime = yy + '-' + mm + '-' + dd

      let yy1 = this.value[1].getFullYear();
      let mm1 = this.value[1].getMonth() + 1;
      let dd1 = this.value[1].getDate();
      this.time.endTime = yy1 + '-' + mm1 + '-' + dd1

      orderList(this.time).then(response => {
        this.orderData = response.data
        this.totalCount = response.data.length
      })
    }
  }
}
</script>

<style scoped>

</style>
