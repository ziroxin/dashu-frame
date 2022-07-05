<template>
  <div class="app-container">
    <!--    操作按钮-->
    <div style="margin-bottom: 20px">
      <el-button type="primary" icon="el-icon-plus" @click="orderBillAdd">添加</el-button>
      <el-button type="info" icon="el-icon-edit" @click="orderBillUpdate">修改</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="orderBillDelete">删除</el-button>
    </div>

    <!--    表格部分-->
    <el-table :data="orderBillData" row-key="orderBillId" style="margin-bottom: 20px;" border
              @selection-change="selectionChangeHandlerOrder"
    >
      <el-table-column type="selection" width="50" header-align="center" align="center" />
      <el-table-column prop="companyName" label="单位名称" sortable />
      <el-table-column prop="taxId" label="纳税人识别号" sortable />
      <el-table-column prop="companyAdress" label="单位地址" sortable />
      <el-table-column prop="companyPhone" label="单位联系电话" sortable />
      <el-table-column prop="bank" label="开户行" sortable />
      <el-table-column prop="bankAccount" label="银行账号" sortable />
      <el-table-column prop="invoiceAmount" label="开票金额" sortable />
      <el-table-column prop="createTime" label="添加时间" sortable />
      <el-table-column prop="updateTime" label="最后修改时间" sortable />
    </el-table>

    <!--    分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--    添加修改数据弹窗-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="orderBillDataForm" :model="temp" :rules="rules" label-position="right" label-width="150px"
               style="width: 500px; margin-left: 50px;"
      >

        <el-form-item label="单位名称：" prop="companyName">
          <el-input v-model="temp.companyName" />
        </el-form-item>

        <el-form-item label="纳税人识别号：" prop="taxId">
          <el-input v-model="temp.taxId" />
        </el-form-item>

        <el-form-item label="单位地址：" prop="companyAdress">
          <el-input v-model="temp.companyAdress" />
        </el-form-item>

        <el-form-item label="单位联系电话：" prop="companyPhone">
          <el-input v-model="temp.companyPhone" />
        </el-form-item>

        <el-form-item label="开户行：" prop="bank">
          <el-input v-model="temp.bank" />
        </el-form-item>

        <el-form-item label="银行账号：" prop="bankAccount">
          <el-input v-model="temp.bankAccount" />
        </el-form-item>

        <el-form-item label="开票金额：" prop="invoiceAmount">
          <el-input v-model="temp.invoiceAmount" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitJudgment">保存</el-button>
        <el-button @click="dialogFormVisible=false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {orderBillAdd, orderBillDelete, orderBillList, orderBillUpdate} from '@/api/order-bill';

export default {
  name: 'Index',
  data() {
    return {
      //发票数据
      orderBillData: [],
      //勾选的数据
      changeData: [],
      //分页
      pager: {page: 1, limit: 10},
      totalCount: 0,
      // 对话框属性
      textMap: {update: '修改', create: '新增'},
      dialogStatus: '',
      dialogFormVisible: false,
      //  每一条数据
      temp: {},
      //表单规则
      rules: {
        companyName: [{required: true, message: '请填写单位名称', trigger: 'blur'}],
        taxId: [{pattern: /[0-9a-zA-Z]{1,6}/, message: '纳税人识别号只可以输入数字和字母', trigger: 'blur'}, {min: 15, max: 20, message: '长度在 15 到 20个字符之间', trigger: 'blur'}],
        bankAccount: [{pattern: /^[+]?(0|([1-9]\d*))?$/, message: '请输入正确数字', trigger: 'blur'}],
        invoiceAmount:[{pattern: /^[+-]?(0|([1-9]\d*))(\.\d{1,2})?$/, message: '请输入正确金额,最多两位小数', trigger: 'blur'}],
        companyPhone: [{
          required: false, trigger: 'blur', validator: (r, v, b) => {
            (v && !(/^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$|^0\d{2,3}-?\d{7,8}$/.test(v))) ? b('手机号或固定电话格式不正确') : b()
          }
        }]
      },
      //删除数据的ID数据
      orderBillIds: []
    }
  },
  created() {
    this.getOrderBillList()
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
    //查询发票数据
    getOrderBillList() {
      orderBillList().then(response => {
        this.orderBillData = response.data
        this.totalCount = response.data.length
      })
    },
    resetTemp() {
      this.temp = {
        orderId: '1',
        companyName: '',
        taxId: '',
        companyAdress: '',
        companyPhone: '',
        bank: '',
        bankAccount: '',
        invoiceAmount: ''
      }
    },
    //点击添加按钮后
    orderBillAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['orderBillDataForm'].clearValidate()
      })
    },
    //点击修改按钮后
    orderBillUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行修改！'})
      } else if (this.changeData.length > 1) {
        this.$message({type: 'warning', message: '修改时仅允许选择一条数据！'})
      } else {
        this.temp = Object.assign({}, this.changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['orderBillDataForm'].clearValidate()
        })
      }
    },
    //  提交数据
    submitJudgment() {
      this.$refs['orderBillDataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'update') {
            orderBillUpdate(this.temp).then(response => {
              const {code} = response
              if (code === '200') {
                this.$message({type: 'success', message: '修改成功！'})
                this.dialogFormVisible = false
                this.getOrderBillList()
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            orderBillAdd(this.temp).then(response => {
              const {code} = response
              if (code === '200') {
                this.$message({type: 'success', message: '发票信息添加成功！'})
                this.dialogFormVisible = false
                this.getOrderBillList()
              } else {
                this.$message({type: 'error', message: '发票信息添加失败！'})
              }
            })
          }
        }
      })
    },
    //  删除发票信息
    orderBillDelete() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行删除！'})
      } else {
        this.$confirm('确定要删除吗？', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          this.orderBillIds.push(...this.changeData.map(r => r.orderBillId))
          orderBillDelete(this.orderBillIds).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '删除成功！'})
              this.getOrderBillList()
            } else {
              this.$message({type: 'error', message: '删除失败！'})
            }
          })
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
