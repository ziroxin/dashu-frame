<template>
  <div class="app-container">
    <!--  操作按钮  -->
    <div style="margin-bottom: 20px; ">
      <el-button type="primary" icon="el-icon-plus" @click="groupAdd">添加</el-button>
      <el-button type="info" icon="el-icon-edit" @click="groupUpdate">修改</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="groupDelete">删除</el-button>
    </div>

    <!--  表格部分  -->
    <el-table :data="groupData" row-key="groupId" style="margin-bottom: 20px;" border
              @selection-change="selectionChangeHandlerOrder"
    >
      <el-table-column type="selection" width="50" header-align="center" align="center" />
      <!--  sortable 排序 -->
      <el-table-column prop="groupName" label="分组名称" sortable>
        <template v-slot="scope">
          <li class="menu-item">
            <item :icon="scope.row.groupIcon" :title="scope.row.groupName" />
          </li>
        </template>
      </el-table-column>
      <el-table-column prop="groupOrder" label="分组显示顺序" sortable />
      <el-table-column prop="createTime" label="添加时间" sortable />
      <el-table-column prop="updateTime" label="最后更新时间" sortable />
    </el-table>

    <!--分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--  弹窗  -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="groupDataForm" :model="temp" :rules="rules" label-position="right" label-width="150px"
               style="width: 500px; margin-left: 50px;"
      >
        <el-form-item label="分组名称" prop="groupName">
          <el-input v-model="temp.groupName" />
        </el-form-item>

        <el-form-item label="图标" prop="groupIcon">
          <IconPicker v-model="temp.groupIcon" :icon="temp.groupIcon" @iconName="getIconName" />
        </el-form-item>

        <el-form-item label="分组显示顺序" prop="groupOrder">
          <el-input v-model="temp.groupOrder" />
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

import {groupAdd, groupDelete, groupList, groupUpdate} from '@/api/group';
// 引入图标选择器
import IconPicker from '@/views/system/menu/IconPicker/index';
// 菜单项目
import Item from '@/layout/components/Sidebar/Item';

export default {
  name: 'Index',
  components: {IconPicker,Item},
  data() {
    return {
      groupData: [],
      //选择的数据
      changeData: [],
      //分页
      pager: {page: 1, limit: 10},
      totalCount: 0,
      //每一条数据
      temp: {},
      //删除的IDS
      groupIds:[],
      // 对话框属性
      textMap: {update: '修改', create: '新增'},
      dialogStatus: '',
      dialogFormVisible: false,
      //表单规则
      rules: {groupName: [{required: true, message: '请填写分组名称', trigger: 'blur'}]}
    }
  },
  created() {
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
    // 查询表格数据
    getGroupList() {
      groupList().then(response => {
        this.groupData = response.data
        this.totalCount = response.data.length
      })
    },
    resetTemp() {
      this.temp = {
        shopId:'',
        groupName: '',
        groupOrder: 0,
        groupIcon: ''
      }
    },
    //点击添加按钮后
    groupAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() =>{
        this.$refs['groupDataForm'].clearValidate()
      })
    },
    //点击修改按钮后
    groupUpdate() {
      if (this.changeData.length <= 0) {
        this.$message({type:'warning',message:'请选择一条数据进行修改！'})
      }else if(this.changeData.length > 1) {
        this.$message({type:'warning',message:'修改时仅允许选择一条数据！'})
      }else{
        this.temp = Object.assign({},this.changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(()=>{
          this.$refs['groupDataForm'].clearValidate()
        })
      }
    },
    //提交数据
    submitJudgment() {
      this.$refs['groupDataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'update') {
            groupUpdate(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type:'success',message:'修改成功！'})
                this.dialogFormVisible = false
                this.getGroupList()
              }else {
                this.$message({type:'error',message:'修改失败！'})
              }
            })
          } else {
            groupAdd(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type: 'success', message: '添加成功！'})
                this.dialogFormVisible = false
                this.getGroupList()
              } else {
                this.$message({type: 'error', message: '添加失败！'})
              }
            })
          }
        }
      })
    },
    groupDelete() {
      if (this.changeData.length <= 0) {
        this.$message({type:'warning',message:'请选择一条数据进行删除！'})
      }else{
        this.$confirm('确定要删除吗？','删除提醒',{
          confirmButtonText:'确定',cancelButtonText:'取消',type:'warning'
        }).then(() =>{
            this.groupIds.push(...this.changeData.map(r=>r.groupId))
          groupDelete(this.groupIds).then(reponse => {
            const {code} = reponse
            if (code === '200') {
              this.$message({type:'success',message:'删除成功！'})
              this.getGroupList()
            }else{
              this.$message({type:'success',message:'删除失败！'})
            }
          })
        })
      }
    },
    // 查询图标名称
    getIconName(value) {
      this.temp.groupIcon = value;
    }
  }
}
</script>

<style scoped>

</style>
