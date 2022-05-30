<template>
  <div class="app-container">

    <!--  操作按钮  -->
    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;" @click="userAdd">新增</el-button>
    <el-button type="primary" icon="el-icon-edit" style="margin-bottom: 20px;" @click="userUpdate">修改</el-button>
    <el-button type="danger" icon="el-icon-delete" style="margin-bottom: 20px;" @click="userDelete">删除</el-button>

    <!-- 表格部分 -->
    <el-table :data="userTable" row-key="userId" style="margin-bottom: 20px;" border @selection-change="selectionChangeHandlerOrder">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="userName" label="用户名" sortable width="220" />
      <el-table-column prop="sex" label="性别" sortable width="220">
        <template slot-scope="scope">
          <span v-if="scope.row.sex === '0'">未知</span>
          <span v-else-if="scope.row.sex === '1'">男</span>
          <span v-else-if="scope.row.sex === '2'">女</span>
        </template>
      </el-table-column>
      <el-table-column prop="nickName" label="昵称" sortable width="220" />
      <el-table-column prop="introduce" label="简介" sortable width="220" />
      <el-table-column prop="avatar" label="头像" sortable width="220" />
      <el-table-column prop="name" label="姓名" sortable width="220" />
      <el-table-column prop="phone" label="手机号" sortable width="220" />
    </el-table>

    <!--分页-->
    <el-pagination style="text-align: center;" background layout="total, pager"
                   :page-size="pager.limit" :current-page="pager.page"
                   :total="totalCount" @current-change="handleCurrentChange"
    />

    <!--  弹窗  -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="userDataForm" :model="temp" :rules="rules" label-position="right" label-width="100px" style="width: 500px; margin-left: 50px;">
        <el-form-item label="用户名：" prop="userName">
          <el-input v-model="temp.userName" />
        </el-form-item>

        <el-form-item label="密码：" prop="password">
          <el-input v-model="temp.password" show-password />
        </el-form-item>

        <el-form-item label="角色：" prop="roleId">
          <el-select v-model="temp.roleId" class="filter-item" placeholder="请选择班级">
            <el-option v-for="item in roleNameOptions" :key="item.roleId" :label="item.roleName" :value="item.roleId" />
          </el-select>
        </el-form-item>

        <el-form-item label="性别：" prop="sex">
          <el-radio v-model="temp.sex" label="0">未知</el-radio>
          <el-radio v-model="temp.sex" label="1">男</el-radio>
          <el-radio v-model="temp.sex" label="2">女</el-radio>
        </el-form-item>

        <el-form-item label="昵称：" prop="nickName">
          <el-input v-model="temp.nickName" />
        </el-form-item>

        <el-form-item label="简介：" prop="introduce">
          <el-input v-model="temp.introduce" />
        </el-form-item>

        <el-form-item label="姓名：" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>

        <el-form-item label="手机号：" prop="phone">
          <el-input v-model="temp.phone" />
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
import {getUserList,userAdd,userUpdate,userDelete} from '@/api/user'
import {getRoleList} from '@/api/role';
export default {
  data() {
    return {
      // 表格数据
      userTable: [],
      //分页
      pager: {page: 1, limit: 10},
      totalCount: 0,
      roleNameOptions:[],
      textMap: {update: '修改', create: '新增'},
      // 对话框属性
      dialogStatus: '',
      dialogFormVisible:false,
      //选中的数据
      changeData:[],
      temp: {},
      rules: {}

    }
  },
  created() {
    this.getUserList()
    this.getRoleList()
  },
  methods: {
    // 表格勾选
    selectionChangeHandlerOrder(val) {
      this.changeData = val
    },
    resetTemp() {
      this.temp = {
        userName:'',
        roleId:'',
        password:'',
        sex:'0',
        nickName:'',
        introduce:'',
        name:'',
        phone:''
      }
    },
    //查询用户列表
    getUserList() {
      getUserList(this.pager).then(response => {
        const {data} = response
        this.totalCount = data.total
        this.userTable = data.records
      })
    },
    //分页
    handleCurrentChange(page) {
      this.pager.page = page
    },
    // 查询角色
    getRoleList() {
      getRoleList().then(response =>{
        const {data} = response
        this.roleNameOptions = data.records
      })
    },
    userAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.$nextTick(() => {
        this.$refs['userDataForm'].clearValidate()
      })
    },
    userUpdate() {
      if(this.changeData.length <= 0) {
        this.$message({type:'warning',message:'请选择一条数据进行修改！'})
      }else if(this.changeData.length > 1) {
        this.$message({type:'warning',message:'修改时，只允许选择一条数据！'})
      }else {
        this.temp = Object.assign({},this.changeData[0])
        this.dialogStatus = 'update'
        this.dialogFormVisible = true
        this.$nextTick(()=>{
          this.$refs['userDataForm'].clearValidate()
        })
      }
    },
    // 提交数据
    submitJudgment() {
      // 表单验证
      this.$refs['userDataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'update') {
            userUpdate(this.temp).then(reponse =>{
              const {code} = reponse
              if (code === '200') {
                this.$message({type:'success',message:'修改成功！'})
                this.dialogFormVisible = false
                this.getUserList()
              }else{
                this.$message({type:'error',message:'修改失败！'})
              }
            })
          }else {
            userAdd(this.temp).then(reponse => {
              const {code} = reponse
              if (code === '200') {
                this.$message({type:'success',message:'添加成功！'})
                this.dialogFormVisible = false
                this.getUserList()
              }else{
                this.$message({type:'error',message:'添加失败！'})
              }
            })
          }
        }
      })
    },
    userDelete() {
      if (this.changeData.length <= 0) {
        this.$message({message: '请选择一条数据删除！', type: 'warning'})
      } else {
        this.$confirm('确定要删除吗?', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          // 执行删除
          const userIds = this.changeData.map(r => r.userId)
          userDelete(userIds).then(response => {
            const {code} = response
            if (code === '200') {
              this.$message({type: 'success', message: '删除成功！'})
              this.getUserList()
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
<style>
</style>

