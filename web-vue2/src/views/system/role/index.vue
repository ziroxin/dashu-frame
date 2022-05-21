<template>
  <div class="app-container">
    <el-row>
      <el-col :span="9">
        <el-table :data="tableData" stripe style="width: 100%" border>
          <el-table-column label="角色" align="center">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="right"
                          :title="scope.row.roleName"
                          :content="scope.row.roleDescription"
              >
                <span slot="reference" style="cursor: pointer;">{{ scope.row.roleName }}</span>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="100">
            <template v-slot="scope">
              <el-button type="text" size="small" @click.native.prevent="setMyApi(scope.row.roleId)">
                设置权限
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination style="text-align: center;" background layout="total, pager"
                       :page-size="pager.limit" :current-page="pager.page"
                       :total="totalCount" @current-change="handleCurrentChange"
        />
      </el-col>
      <el-col :span="15" style="padding-left: 20px;border-left: 1px solid #dedede;" />
    </el-row>
  </div>
</template>

<script>
import {getRoleList} from '@/api/role'

export default {
  data() {
    return {
      pager: {page: 1, limit: 10},
      totalCount: 0,
      tableData: []
    }
  },
  created() {
    this.loadRoleList()
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
    handleCurrentChange(page) {
      console.log(`当前页: ${page}`);
    }
  }
}
</script>
