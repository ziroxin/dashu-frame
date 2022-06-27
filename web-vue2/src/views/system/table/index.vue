<template>
  <div class="app-container">
    <!--  操作按钮-->
    <div style="margin-bottom: 20px;">
      <el-button type="primary" icon="el-icon-plus" @click="tableAdd">添加</el-button>
      <el-button type="info" icon="el-icon-edit" @click="tableUpdate">修改</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="tableDelete">删除</el-button>
    </div>

    <!--  表格部分-->
    <el-table :data="tableData" row-key="tableId" style="margin-bottom: 20px;" border
              @selection-change="selectionChangeHandlerOrder"
    >
      <el-table-column type="selection" width="50" header-align="center" align="center" />
      <el-table-column prop="tableName" label="餐桌名称" header-align="center" align="center" />
    </el-table>

    <!--分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--  弹窗-->
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
  </div>
</template>

<script>
import {tableAdd, tableDelete, tableList, tableUpdate} from '@/api/table';

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
      //每一条数据
      temp: {},
      //表单验证规则
      rules: {},
      //删除的IDS
      tableIds:[]
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
            tableUpdate(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '修改成功！'})
                this.dialogFormVisible = false
                this.getTableList()
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            tableAdd(this.temp).then(reponse => {
              const {code} = reponse
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
        this.$message({type:'warning',message:'请选择一条数据进行删除！'})
      }else{
        this.$confirm('确定要删除吗？','删除提醒',{
          confirmButtonText:'确定',cancelButtonText:'取消',type:'warning'
        }).then(()=>{
          this.tableIds.push(...this.changeData.map(r=>r.tableId))
          tableDelete(this.tableIds).then(reponse =>{
            const {code} = reponse
            if (code === '200') {
              this.$message({type:'success',message:'删除成功！'})
              this.getTableList()
            }else {
              this.$message({type:'error',message:'删除失败！'})
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
