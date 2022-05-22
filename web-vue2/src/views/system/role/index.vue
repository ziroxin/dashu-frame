<template>
  <div class="app-container">
    <el-row>
      <el-col :span="9">
        <!--        角色管理按钮-->
        <div style="margin-bottom: 20px;">
          <el-button type="primary" plain icon="el-icon-edit" @click="roleAdd">新增</el-button>
          <el-button type="success" plain icon="el-icon-edit" @click="roleUpdate">修改</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="roleDelete">删除</el-button>
        </div>
        <!--        角色管理表格-->
        <el-table :data="tableData" stripe style="width: 100%" border
                  @selection-change="handleTableSelectChange">
          <el-table-column type="selection" width="55"/>
          <el-table-column label="角色" align="center">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="right"
                          :title="scope.row.roleName" :content="scope.row.roleDescription">
                <span slot="reference" style="cursor: pointer;">{{ scope.row.roleName }}</span>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="100">
            <template v-slot="scope">
              <el-button type="text" size="small" @click.native.prevent="setMyApi(scope.row.roleId)"> 设置权限</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--        分页-->
        <el-pagination style="text-align: center;" background layout="total, pager"
                       :page-size="pager.limit" :current-page="pager.page"
                       :total="totalCount" @current-change="handleCurrentChange"
        />
        <!--        添加修改弹窗-->
        <el-dialog :title="titleMap[dialogType]" :visible.sync="dialogFormVisible">
          <el-form ref="roleDateForm" :model="temp" label-position="left" label-width="100px"
                   style="width: 500px; margin-left: 50px;">
            <el-form-item label="角色名称" prop="roleName" :rules="{required: true, message: '角色名称不能为空'}">
              <el-input v-model="temp.roleName"/>
            </el-form-item>
            <el-form-item label="角色描述" prop="roleDescription">
              <el-input type="textarea" v-model="temp.roleDescription"/>
            </el-form-item>
            <el-form-item label="角色顺序" prop="roleOrder"
                          :rules="[{required: true, message: '角色顺序不能为空'},{type: 'number', message: '必须为数字'}]">
              <el-input v-model.number="temp.roleOrder"/>
            </el-form-item>
          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="saveRoles">保存</el-button>
            <el-button @click="dialogFormVisible=false">取消</el-button>
          </div>

        </el-dialog>
      </el-col>
      <el-col :span="15" style="padding-left: 20px;border-left: 1px solid #dedede;">

      </el-col>
    </el-row>
  </div>
</template>

<script>
import {addRole, deleteRoles, getRoleList, updateRole} from '@/api/role'

export default {
  data() {
    return {
      //角色表格数据
      pager: {page: 1, limit: 10},
      totalCount: 0,
      tableData: [],
      tableSelectRows: [],
      //添加修改弹窗
      titleMap: {add: '添加角色', update: '修改角色'},
      dialogType: '',
      dialogFormVisible: false,
      temp: {}
    }
  },
  created() {
    this.loadRoleList()
    this.resetTemp()
  },
  methods: {
    loadRoleList() {
      getRoleList(this.pager)
        .then((response) => {
          const {data} = response
          this.totalCount = data.total
          this.tableData = data.records
          console.dir(data)
          // this.tableData = data
        })
    },
    handleTableSelectChange(rows) {
      this.tableSelectRows = rows
    },
    handleCurrentChange(page) {
      this.pager.page = page
    },
    resetTemp() {
      this.temp = {
        roleId: '',
        roleName: '',
        roleDescription: '',
        roleOrder: 1
      }
    },
    roleAdd() {
      this.resetTemp()
      this.dialogFormVisible = true
      this.dialogType = 'add'
      this.$nextTick(() => {
        this.$refs['roleDateForm'].clearValidate()
      })
    },
    roleUpdate() {
      if (this.tableSelectRows.length <= 0) {
        this.$message({message: '请选择一条数据修改！', type: 'warning'});
      } else if (this.tableSelectRows.length > 1) {
        this.$message({message: '修改时，只允许选择一条数据！', type: 'warning'});
      } else {
        // 修改弹窗
        this.temp = Object.assign({}, this.tableSelectRows[0])
        this.dialogType = 'update'
        this.dialogFormVisible = true
        this.$nextTick(() => {
          this.$refs['roleDateForm'].clearValidate()
        })
      }
    },
    roleDelete() {
      if (this.tableSelectRows.length <= 0) {
        this.$message({message: '请选择一条数据删除！', type: 'warning'});
      } else {
        this.$confirm('确定要删除吗?', '删除提醒', {
          confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
        }).then(() => {
          // 执行删除
          const roleIds = this.tableSelectRows.map(r => r.roleId);
          deleteRoles(roleIds).then(response => {
            const {code} = response
            if (code === "200") {
              this.$message({type: 'success', message: '删除成功！'})
              this.loadRoleList()
            } else {
              this.$message({type: 'error', message: '删除失败！'})
            }
          })
        });
      }
    },
    saveRoles() {
      this.$refs['roleDateForm'].validate((valid) => {
        if (valid) {
          if (this.dialogType === "update") {
            updateRole(this.temp).then(response => {
              const {code} = response
              if (code === "200") {
                this.$message({type: 'success', message: '修改成功！'})
                this.loadRoleList()
                this.dialogFormVisible = false
              } else {
                this.$message({type: 'error', message: '修改失败！'})
              }
            })
          } else {
            addRole(this.temp).then(response => {
              const {code} = response
              if (code === "200") {
                this.$message({type: 'success', message: '添加成功！'})
                this.loadRoleList()
                this.dialogFormVisible = false
              } else {
                this.$message({type: 'error', message: '添加失败！'})
              }
            })
          }
        }
      })
    }
  }
}
</script>
