import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import { Modal, Button } from 'react-bootstrap';

import apiService from '../../services/apiService';
import stateService from '../../services/stateService';
import authService from '../../services/authService';
import '../../assets/css/user-buttons.css';

class Users extends Component {
    constructor(props) {
        super(props);

        this.state = {
            departmentId: 0,
            email: '',
            id: 0,
            name: '',
            roleId: 0,
            salary: 0,
            startingDate: '',
            employees: [],
            show: false,
            selectedUser: {
                departmentId: 0,
                email: '',
                id: 0,
                name: '',
                roleId: 0,
                salary: 0,
                password: ''
            }
        };
    }

    handleClose() {
        this.setState({ show: false });
    }

    async handleShow(id) {
        this.setState({ id: id, show: true });
        try {
            const result = await apiService.sendRequest('Users/' + id, 'GET');
            if (result.data.status === 'fail') {
                stateService.getFunction('showNotification')('error', '', result.data.message);
            } else {
                const { departmentId, email, id, name, roleId, salary } = result.data.data;
                this.setState({ selectedUser: { departmentId, email, id, name, roleId, salary, password:'' } });
            }
        } catch (err) {
            console.log(err);
        }
    }

    onChangeName(e) {
        const name = e.target.value;
        const { departmentId, email, id, roleId, salary, password } = this.state.selectedUser;
        this.setState({ selectedUser: { name, departmentId, email, id, password, roleId, salary } });
    }

    onChangeEmail(e) {
        const email = e.target.value;
        const { name, departmentId, id, roleId, salary, password } = this.state.selectedUser;
        this.setState({ selectedUser: { name, departmentId, email, id, password, roleId, salary } });
    }

    onChangePassword(e) {
        const password = e.target.value;
        const { name, departmentId, email, id, roleId, salary } = this.state.selectedUser;
        this.setState({ selectedUser: { name, departmentId, email, id, password, roleId, salary } });
    }

    onChangeSalary(e) {
        const salary = e.target.value;
        const { name, departmentId, email, id, roleId, password, startingDate } = this.state.selectedUser;
        this.setState({ selectedUser: { name, departmentId, email, id, password, roleId, salary, startingDate } });
    }

    onChangeRoleId(e) {
        const roleId = e.target.value;
        const { name, departmentId, email, id, salary, password, startingDate } = this.state.selectedUser;
        this.setState({ selectedUser: { name, departmentId, email, id, password, roleId, salary, startingDate } });
    }

    onChangeDepartmentId(e) {
        const departmentId = e.target.value;
        const { name, email, id, roleId, salary, password, startingDate } = this.state.selectedUser;
        this.setState({ selectedUser: { name, departmentId, email, id, password, roleId, salary, startingDate } });
    }

    async doDelete(id) {
        const x = window.confirm("Are you sure you want to delete this entry?!");
        if (!x)
            return;
        try {
            const result = await apiService.sendRequest('Users/' + id, 'DELETE');
            if (result.data.status === 'fail') {
                stateService.getFunction('showNotification')('error', 'Error', result.data.message);
            } else {
                stateService.getFunction('showNotification')('success', 'Success', result.data.message);
            }
            try {
                const result = await apiService.sendRequest('Users/', 'GET');
                if (result.data.status === 'fail') {
                    stateService.getFunction('showNotification')('error', '', result.data.message);
                }
                this.setState({ employees: result.data.data });
            } catch (err) {
                console.log(err);
            }
        } catch (err) {
            console.log(err);
        }

    }

    async doEdit() {
        try {
            const body = this.state.selectedUser;
            const result = await apiService.sendRequest('Users/' + this.state.selectedUser.id, 'PUT',body);
            if (result.data.status === 'fail') {
                stateService.getFunction('showNotification')('error', 'Error', result.data.message);
            } else {
                stateService.getFunction('showNotification')('success', 'Success', result.data.message);
                this.handleClose();
            }
            try {
                const result = await apiService.sendRequest('Users/', 'GET');
                if (result.data.status === 'fail') {
                    stateService.getFunction('showNotification')('error', '', result.data.message);
                }else
                this.setState({ employees: result.data.data });
            } catch (err) {
                console.log(err);
            }
        } catch (err) {
            console.log(err);
        }

    }

    componentWillMount() {
        const token = localStorage.getItem('token');
        const isSignIn = authService.checkSession();
        if (!token || !isSignIn) this.props.history.replace('/');
    }

    async componentDidMount() {
        const token = localStorage.getItem('token');
        const payload = token.split('.')[1];
        const { departmentId, email, id, name, roleId, salary, startingDate } = JSON.parse(JSON.parse(atob(payload)).user);
        try {
            const result = await apiService.sendRequest('Users/', 'GET');
            if (result.data.status === 'fail') {
                stateService.getFunction('showNotification')('error', '', result.data.message);
            }
            this.setState({ departmentId, email, id, name, roleId, salary, startingDate, employees: result.data.data });
        } catch (err) {
            console.log(err);
        }
    }


    render() {
        const showBtnsClass = this.state.roleId === 1 ? '' : 'admin-btn-hide';

        return (
            <div className="container">
                <Modal show={this.state.show} onHide={() => this.handleClose()}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <form className="form-horizontal" onSubmit={(e) => this.doEdit(e)}>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label"> Name</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-user fa" aria-hidden="true"></i></span>
                                        <input value={this.state.selectedUser.name} type="text" onChange={(e) => this.onChangeName(e)} className="form-control" name="name" id="name" placeholder="Enter  Name" autoComplete='name' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Email</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-envelope fa" aria-hidden="true"></i></span>
                                        <input value={this.state.selectedUser.email} type="text" onChange={(e) => this.onChangeEmail(e)} className="form-control" name="email" id="email" placeholder="Enter  Email" autoComplete='email' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Password</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                        <input value={this.state.selectedUser.password} type="password" onChange={(e) => this.onChangePassword(e)} className="form-control" name="password" id="password" placeholder="Enter  Password" autoComplete='current-password' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Salary</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-money fa" aria-hidden="true"></i></span>
                                        <input value={this.state.selectedUser.salary} type="numeric" onChange={(e) => this.onChangeSalary(e)} className="form-control" name="salary" id="salary" placeholder="Enter Salary" />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Department Id</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <select value={this.state.selectedUser.departmentId} name="departmentId" id="departmentId" onChange={(e) => this.onChangeDepartmentId(e)}>
                                            <option value={1}>Adminstration</option>
                                            <option value={2}>Beverages</option>
                                            <option value={3}>Bread/Bakery</option>
                                            <option value={4}>Dairies</option>
                                            <option value={5}>Pastas</option>
                                            <option value={6}>Meats</option>
                                            <option value={7}>Fruits</option>
                                            <option value={8}>Vegetables</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Role Id</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <select value={this.state.selectedUser.roleId} name="roleId" id="roleId" onChange={(e) => this.onChangeRoleId(e)}>
                                            <option value={1}>Admin</option>
                                            <option value={2}>Employee</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={() => this.handleClose()}>Close</Button>
                        <Button onClick={() => this.doEdit()}>Edit User</Button>
                    </Modal.Footer>
                </Modal>
                <div className="row col-md-12 custyle">
                    <Link to="/add" className={`btn btn-primary btn-xs pull-left ${showBtnsClass}`}><b>+</b> Add New User</Link>
                    <Link to="/" className={`btn btn-primary btn-danger btn-xs pull-right`}> Log Out</Link>
                    <table className="table table-striped custab">
                        <thead>
                            <tr>
                                <th className="text-center">Name</th>
                                <th className="text-center">Email</th>
                                <th className="text-center">Salary</th>
                                <th className="text-center">Starting Date</th>
                                <th className="text-center">Department Id</th>
                                <th className="text-center">Role Id</th>
                                <th className="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.employees.map((emp, index) => {
                                return (
                                    <tr key={index}>
                                        <td className="text-center">{emp.name}</td>
                                        <td className="text-center">{emp.email}</td>
                                        <td className="text-center">{emp.salary}</td>
                                        <td className="text-center">{emp.startingDate}</td>
                                        <td className="text-center">{emp.departmentId}</td>
                                        <td className="text-center">{emp.roleId}</td>
                                        <td className="text-center"><button onClick={() => this.handleShow(emp.id)} className={`btn btn-primary btn-info btn-xs ${showBtnsClass}`} ><span className="glyphicon glyphicon-edit"></span> Edit</button> <a onClick={() => this.doDelete(emp.id)} className={`btn btn-danger btn-xs ${showBtnsClass}`}><span className="glyphicon glyphicon-remove"></span> Del</a></td>
                                    </tr>
                                );
                            })}

                        </tbody>
                    </table>
                </div>
                <div>

                </div>
            </div>

        );
    }
}

export default Users;