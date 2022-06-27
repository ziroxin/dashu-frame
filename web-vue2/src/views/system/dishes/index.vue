<template>
  <div class="app-container">
    <!--    操作按钮-->
    <div style="margin-bottom: 20px">
      <el-button type="primary" icon="el-icon-plus" @click="dishesAdd">添加</el-button>
      <el-button type="info" icon="el-icon-edit" @click="dishesUpdate">修改</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="dishesDelete">删除</el-button>
    </div>

    <!--    表格部分-->
    <el-table :data="dishesData" row-key="dishesId" style="margin-bottom: 20px;" border
              @selection-change="selectionChangeHandlerOrder"
    >
      <el-table-column type="selection" width="50" header-align="center" align="center" />
      <el-table-column prop="dishesName" label="菜品名称" sortable />
      <el-table-column prop="introduction" label="菜品简介" sortable />
      <el-table-column prop="picture" label="展示图" sortable />
      <el-table-column prop="groupName" label="菜品分组" />
      <el-table-column prop="originalPrice" label="原价" sortable />
      <el-table-column prop="currentPrice" label="现价" sortable />
      <el-table-column prop="state" label="菜品状态" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.state === '0'">已上架</span>
          <span v-else-if="scope.row.state === '1'">已下架</span>
        </template>
      </el-table-column>
      <el-table-column prop="recommend" label="是否推荐" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.recommend === '0'">推荐</span>
          <span v-else-if="scope.row.recommend === '1'">不推荐</span>
        </template>
      </el-table-column>
      <el-table-column prop="dishesOrder" label="菜品顺序" sortable />
    </el-table>

    <!--    分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--    弹窗-->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dishesDataForm" :model="temp" :rules="rules" label-position="right" label-width="150px"
               style="width: 500px; margin-left: 50px;"
      >

        <el-form-item label="菜品名称" prop="dishesName">
          <el-input v-model="temp.dishesName" />
        </el-form-item>

        <el-form-item label="菜品简介" prop="introduction">
          <el-input v-model="temp.introduction" />
        </el-form-item>

        <el-form-item label="展示图" prop="picture">
          <el-input v-model="temp.picture" />
        </el-form-item>

        <el-form-item label="菜品分组" prop="groupId">
          <el-select v-model="temp.groupId" class="filter-item" placeholder="请选择分组">
            <el-option v-for="item in groupNameOptions" :key="item.groupId" :label="item.groupName" :value="item.groupId" />
          </el-select>
        </el-form-item>

        <el-form-item label="原价" prop="originalPrice">
          <el-input v-model="temp.originalPrice" />
        </el-form-item>

        <el-form-item label="现价" prop="currentPrice">
          <el-input v-model="temp.currentPrice" />
        </el-form-item>

        <el-form-item label="菜品状态" prop="state">
          <el-radio v-model="temp.state" label="0">上架</el-radio>
          <el-radio v-model="temp.state" label="1">下架</el-radio>
        </el-form-item>

        <el-form-item label="是否推荐" prop="recommend">
          <el-radio v-model="temp.recommend" label="0">推荐</el-radio>
          <el-radio v-model="temp.recommend" label="1">不推荐</el-radio>
        </el-form-item>

        <el-form-item label="菜品顺序" prop="dishesOrder">
          <el-input v-model="temp.dishesOrder" placeholder="请填写数字" />
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
import {dishesAdd, dishesDelete, dishesList, dishesUpdate} from '@/api/dishes';
import {groupList} from '@/api/group';

export default {
  name: 'Index',
  data() {
    return {
      dishesData: [],
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
      //菜品分组数据
      groupNameOptions:[],
      //删除的IDS
      dishesIds: [],
      //表单规则
      rules: {}
    }
  },
  created() {
    this.getDishesList()
    this.getGroupList()
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
    //查询菜品信息
    getDishesList() {
      dishesList().then(response => {
        this.dishesData = response.data
        this.totalCount = response.data.length
      })
    },
    //查询菜品分组信息
    getGroupList() {
      groupList().then(response => {
        this.groupNameOptions = response.data
      })
    },
    resetTemp() {
      this.temp = {
        dishesName: '',
        introduction: '',
        picture: '',
        originalPrice: '',
        currentPrice: '',
        state: '0',
        recommend: '0',
        dishesOrder: 0,
        groupId:''
      }
    },
    //点击添加按钮后
    dishesAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['dishesDataForm'].clearValidate()
      })
    },
    //点击修改按钮后
    dishesUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择一条数据进行修改！'})
      } else if (this.changeData.length > 1) {
        this.$message({type: 'warning', message: '修改时仅允许选择一条数据！'})
      } else {
        this.temp = Object.assign({}, this.changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['dishesDataForm'].clearValidate()
        })
      }
    },
    //  提交数据
    submitJudgment() {
      this.$refs['dishesDataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'update') {
            console.log(this.temp)
            dishesUpdate(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '修改成功！'})
                this.dialogFormVisible = false
                this.getDishesList()
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            dishesAdd(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '添加成功！'})
                this.dialogFormVisible = false
                this.getDishesList()
              } else {
                this.$message({type: 'error', message: '添加失败！'})
              }
            })
          }
        }
      })
    },
    //点击删除按钮后
    dishesDelete() {
      if (this.changeData.length <= 0) {
        this.$message({type: 'warning', message: '请选择需要删除的数据！'})
      } else {
        this.$confirm('确定要删除吗？', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          this.dishesIds.push(...this.changeData.map(r => r.dishesId))
          dishesDelete(this.dishesIds).then(reponse => {
            const {code} = reponse
            if (code === '200') {
              this.$message({type: 'success', message: '删除成功！'})
              this.getDishesList()
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
