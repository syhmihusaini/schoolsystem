<template>
  <div>
    <b-container>
      <b-row>
        <b-col class="col-8">
          <b-form-input
            v-model="searchId"
            placeholder="Enter Student ID"
          ></b-form-input>
        </b-col>
        <b-col class="col-2">
          <b-button @click="findStudent" variant="primary">Search</b-button>
        </b-col>
        <b-col class="col-2">
          <b-button v-b-modal.add-modal variant="danger">Add Student</b-button>
        </b-col>
      </b-row>
      <br />
      <vue-good-table :columns="columns" :rows="rows">
        <template slot="table-row" slot-scope="props">
          <span v-if="props.column.field == 'actions'">
            <b-dropdown variant="link" size="sm" text="Action" no-caret>
              <b-dropdown-item
                v-b-modal.edit-modal
                class="primary-color"
                @click="selectEditStudent(props.row)"
                >Edit</b-dropdown-item
              >
              <b-dropdown-item
                v-b-modal.delete-modal
                class="primary-color"
                @click="selectDeleteStudent(props.row)"
                >Delete</b-dropdown-item
              >
            </b-dropdown>
          </span>
        </template>
      </vue-good-table>
    </b-container>

    <b-modal id="add-modal" title="Add Student" @ok="handleOk">
      <b-form-input
        v-model="studentName"
        placeholder="Enter student name"
        required
      ></b-form-input>
    </b-modal>
    <b-modal ref="error">Name is required</b-modal>
    <b-modal id="edit-modal" title="Edit Student" @ok="editStudent">
      <b-container fluid>
        <b-row class="my-1">
          <b-col sm="2">
            <label>Student Id:</label>
          </b-col>
          <b-col sm="10">
            <b-form-input
              size="sm"
              :value="selectedStudent.studentId"
              disabled
            ></b-form-input>
          </b-col>
        </b-row>
        <b-row class="my-1">
          <b-col sm="2">
            <label>Student Name:</label>
          </b-col>
          <b-col sm="10">
            <b-form-input size="sm" v-model="editStudentName"></b-form-input>
          </b-col>
        </b-row>
      </b-container>
    </b-modal>

    <b-modal id="delete-modal" title="Delete Student" @ok="deleteStudent">
      <p class="my-4">Are you sure you want to delete?</p>
    </b-modal>
  </div>
</template>

<script>
export default {
  mounted() {
    this.getAllStudent();
  },
  data() {
    return {
      columns: [
        {
          label: "Student ID",
          field: "studentId",
          thStyle: { width: "45%" },
        },
        {
          label: "Student Name",
          field: "studentName",
          thStyle: { width: "45%" },
        },
        {
          label: "",
          field: "actions",
          tdClass: "text-center",
          thStyle: { width: "5%" },
        },
      ],
      rows: [],
      studentName: "",
      searchId: "",
      editStudentName: "",
      selectedStudent: {},
    };
  },
  methods: {
    getAllStudent() {
      fetch("/etiqa/all")
        .then((response) => response.json())
        .then((data) => {
          this.rows = data;
        });
    },
    handleOk() {
      if (this.studentName == "") {
        this.$refs['error'].show();
      } else {
        fetch("/etiqa/add?name=" + this.studentName)
          .then((response) => response.text())
          .then((data) => {
            alert(data);
            this.getAllStudent();
          });
      }
    },
    selectEditStudent(row) {
      this.editStudentName = row.studentName;
      this.selectedStudent = row;
    },
    selectDeleteStudent(row) {
      this.selectedStudent = row;
    },
    editStudent() {
      fetch(
        "/etiqa/edit?id=" +
          this.selectedStudent.studentId +
          "&name=" +
          this.editStudentName
      )
        .then((response) => response.text())
        .then((data) => {
          alert(data);
          this.getAllStudent();
        });
    },
    deleteStudent() {
      fetch("/etiqa/delete?id=" + this.selectedStudent.studentId)
        .then((response) => response.text())
        .then((data) => {
          alert(data);
          this.getAllStudent();
        });
    },
    findStudent() {
      if (this.searchId) {
        fetch("/etiqa/find?id=" + this.searchId)
          .then((response) => response.json())
          .then((data) => {
            this.rows = data ? [data] : [];
          });
      } else {
        this.getAllStudent();
      }
    },
  },
};
</script>