<template>
  <div class="app-container">
    <!--  操作按钮-->
    <div style="margin-bottom: 20px;">
      <el-button type="primary" icon="el-icon-plus" @click="tableAdd">添加</el-button>
      <el-button type="info" icon="el-icon-edit" @click="tableUpdate">修改</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="tableDelete">删除</el-button>
      <el-button type="success" icon="el-icon-check" @click="tableCodeAdd">生成二维码</el-button>
    </div>

    <!--  表格部分-->
    <el-table :data="tableData" row-key="tableId" style="margin-bottom: 20px;" border
              @selection-change="selectionChangeHandlerOrder"
    >
      <el-table-column type="selection" width="50" header-align="center" align="center" />
      <el-table-column prop="tableName" label="餐桌名称" header-align="center" align="center" />
      <el-table-column prop="picture" label="二维码" sortable>
        <template slot-scope="scope">
          <el-image style="width: 50px;height:50px;" class="tableImg" :src="imgUrl+scope.row.qrcodeFilePath" />
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="210" header-align="center" align="center">
        <template slot-scope="{row}">
          <el-button type="text" @click="lookTableCode(row)">查看二维码</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--  添加修改弹窗-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="tableDataForm" :model="temp" :rules="rules" label-position="right" label-width="150px"
               style="width: 500px; margin-left: 50px;"
      >
        <el-form-item label="餐桌名称" prop="tableName">
          <el-input v-model="temp.tableName" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitJudgment">保存</el-button>
        <el-button @click="dialogFormVisible=false">取消</el-button>
      </div>
    </el-dialog>

    <!--  二维码弹窗-->
    <el-dialog :visible.sync="dialogFormCode">
      <div style="text-align: center;">
        <el-image :src="imgUrl+orderCodeData.qrcodeFilePath" />
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {lookTableCode, tableAdd, tableCodeAdd, tableDelete, tableList, tableUpdate} from '@/api/table';

export default {
  name: 'Index',
  data() {
    return {
      //表格数据
      tableData: [],
      //勾选的数据
      changeData: [],
      //分页
      pager: {page: 1, limit: 10},
      totalCount: 0,
      // 对话框属性
      textMap: {update: '修改', create: '新增'},
      dialogStatus: '',
      dialogFormVisible: false,
      dialogFormCode: false,
      //每一条数据
      temp: {},
      //表单验证规则
      rules: {tableName: [{required: true, message: '请填写餐桌名称', trigger: 'blur'}]},
      //删除或生成餐桌二维码的的IDS
      tableIds: [],
      //餐桌详情数据
      orderCodeData: {
        tableId: '',
        shopId: '',
        qrcodeFileName: '',
        qrcodeFilePath: ''
      },
      imgUrl: process.env.VUE_APP_IMG
    }
  },
  created() {
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
    // 查询表格数据
    getTableList() {
      tableList().then(response => {
        this.tableData = response.data
        this.totalCount = response.data.length
      })
    },
    resetTemp() {
      this.temp = {
        tableName: ''
      }
    },
    //  点击添加按钮后
    tableAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['tableDataForm'].clearValidate()
      })
    },
    tableUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行修改！'})
      } else if (this.changeData.length > 1) {
        this.$message({type: 'warning', message: '修改时仅允许选择一条数据！'})
      } else {
        this.temp = Object.assign({}, this.changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['tableDataForm'].clearValidate()
        })
      }
    },
    //提交数据
    submitJudgment() {
      this.$refs['tableDataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'update') {
            tableUpdate(this.temp).then(response => {
              const {code} = response
              if (code === '200') {
                this.$message({type: 'success', message: '修改成功！'})
                this.dialogFormVisible = false
                this.getTableList()
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            tableAdd(this.temp).then(response => {
              const {code} = response
              if (code === '200') {
                this.$message({type: 'success', message: '添加成功！'})
                this.dialogFormVisible = false
                this.getTableList()
              } else {
                this.$message({type: 'error', message: '添加失败！'})
              }
            })
          }
        }
      })
    },
    tableDelete() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行删除！'})
      } else {
        this.$confirm('确定要删除吗？', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          this.tableIds.push(...this.changeData.map(r => r.tableId))
          tableDelete(this.tableIds).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '删除成功！'})
              this.getTableList()
            } else {
              this.$message({type: 'error', message: '删除失败！'})
            }
          })
        })
      }
    },
    tableCodeAdd() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择餐桌生成二维码！'})
      } else {
        let table = '';
        for (let i = 0; i < this.changeData.length; i++) {
          if (this.changeData[i].qrcodeFilePath !== null) {
            table += this.changeData[i].tableName + '、'
          }
        }
        if (table !== '') {
          table = table.substring(0, table.lastIndexOf('、'))
          this.$alert(table + '已有二维码')
        } else {
          this.tableIds.push(...this.changeData.map(r => r.tableId))
          console.log(this.tableIds);
          tableCodeAdd(this.tableIds).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '二维码生成成功！'})
            } else {
              this.$message({type: 'error', message: '二维码生成失败！'})
            }
          })
        }
      }
    },
    lookTableCode(row) {
      const tableId = row.tableId
      lookTableCode(tableId).then(response => {
        // this.orderCodeData = response.data
        this.orderCodeData = Object.assign({}, response.data)
        console.log(this.orderCodeData.qrcodeFileName)
        console.log(this.orderCodeData)
      })
      this.dialogFormCode = true
    }
  }
}
</script>

<style lang="scss" scoped>
.foods {
  .item {
    margin-top: 24 rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .left {
      display: flex;

      .img-box {
        image {
          width: 1000 rpx;
          height: 1000 rpx;
          border-radius: 4 rpx;
        }

        .img-status-text {
          font-size: 24 upx;
        }
      }

      .food-info {
        margin-left: 10 rpx;

        .food-name {
          color: #333333;
          font-size: 28 upx;
          font-weight: bold;
        }

        .food-number {
          margin-top: 24 rpx;
          color: #777777;
          font-size: 26 upx;
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
</style>
