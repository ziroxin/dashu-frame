<template>
  <div class="app-container">
    <el-row>
      <el-col :span="9">
        <div class="grid-content bg-purple">
          <el-table v-loading="listLoading" default-expand-all style="width: 95%;margin-bottom: 20px;" border
                    :data="tableData" row-key="permissionId"
                    :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
            <el-table-column label="名称" sortable width="180">
              <template slot-scope="scope">
                {{ scope.row.permissionTitle }}
                <el-tag v-if="scope.row.permissionType === '0'" disable-transitions>路由</el-tag>
                <el-tag v-if="scope.row.permissionType === '1'" disable-transitions type="warning">按钮</el-tag>
                <el-tag v-if="scope.row.permissionType === '2'" disable-transitions type="success">外链</el-tag>
                <el-tag v-if="scope.row.permissionType === '3'" disable-transitions type="info">其他</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="permissionRouter" label="路由" sortable width="180"></el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
              <template>
                <el-button type="text" size="small" @click.native.prevent="deleteRow(scope.$index, tableData)">
                  设置API
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="15">
        <div class="grid-content bg-purple-light">
          <div style="float: right;margin-bottom: 20px;">
            <el-button type="danger" @click="scanApi()">扫描API</el-button>
          </div>
          <el-table v-loading="listLoading2" default-expand-all style="width: 100%;margin-bottom: 20px;" border
                    :data="tableData2" row-key="apiId">
            <el-table-column prop="apiName" label="名称" sortable width="100"></el-table-column>
            <el-table-column prop="apiPermission" label="权限标签" sortable width="100"></el-table-column>
            <el-table-column prop="apiRequestUrl" label="URL" sortable width="100"></el-table-column>
            <el-table-column prop="apiRequestMethod" label="METHOD" sortable width="100"></el-table-column>
            <el-table-column prop="apiDescription" label="描述" sortable width="100"></el-table-column>
            <el-table-column prop="apiClassName" label="类名" sortable width="100"></el-table-column>
            <el-table-column prop="apiMethodName" label="方法名" sortable width="100"></el-table-column>
          </el-table>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import {getApiList, permissionTreeList, scanApi} from '@/api/api'

export default {
  data() {
    return {
      tableData: [],
      tableData2: [],
      listLoading: true,
      listLoading2: true
    }
  },
  created() {
    this.getPermissionTreeList()
    this.getApiList();
  },
  methods: {
    async getPermissionTreeList() {
      this.listLoading = true
      const {data} = await permissionTreeList()
      console.dir(data)
      this.tableData = data
      // const items = data.items
      // this.list = items.map(v => {
      //   this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
      //   v.originalTitle = v.title //  will be used when user click the cancel botton
      //   return v
      // })
      this.listLoading = false
    },
    async getApiList() {
      this.listLoading2 = true
      const {data} = await getApiList()
      console.dir(data.records)
      this.tableData2 = data.records
      this.listLoading2 = false
    },
    async scanApi() {
      await scanApi()
      this.getApiList()
    }
  }
}
</script>
