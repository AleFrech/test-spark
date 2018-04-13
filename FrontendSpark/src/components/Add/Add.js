import React, { Component } from 'react'

import apiService from '../../services/apiService';
import stateService from '../../services/stateService';
import authService from '../../services/authService';

class Add extends Component {

    constructor(props) {
        super(props);

        this.state = {
            departmentId: 0,
            email: '',
            password: '',
            name: '',
            roleId: 0,
            salary: 0
        };
    }

    onChangeName(e) {
        const name = e.target.value;
        this.setState({ name });
    }

    onChangeEmail(e) {
        const email = e.target.value;
        this.setState({ email });
    }

    onChangePassword(e) {
        const password = e.target.value;
        this.setState({ password });
    }

    onChangeSalary(e) {
        const salary = e.target.value;
        this.setState({ salary });
    }

    onChangeRoleId(e) {
        const roleId = e.target.value;
        this.setState({ roleId });
    }

    onChangeDepartmentId(e) {
        const departmentId = e.target.value;
        this.setState({ departmentId });
    }

    componentWillMount() {
        const token = localStorage.getItem('token');
        const isSignIn = authService.checkSession();
        if (!token || !isSignIn) this.props.history.replace('/');
    }

    async doAdd(e) {
        e.preventDefault();
        console.log(this.state);

        const body = this.state;

        try {
            const result = await apiService.sendRequest('Users/', 'POST', body);
            const { message, status } = result.data;
            if (status === 'fail') {
                stateService.getFunction('showNotification')('error', 'Error', message);
            }
            else {
                stateService.getFunction('showNotification')('success', 'Success', message);
                this.props.history.push('/users');
            }


        } catch (err) {
            console.log(err);
        }
    }

    render() {
        return (
            <div className="container">
                <div className="row main">
                    <div className="main-login main-center">
                        <form className="form-horizontal" onSubmit={(e) => this.doAdd(e)}>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label"> Name</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-user fa" aria-hidden="true"></i></span>
                                        <input type="text" onChange={(e) => this.onChangeName(e)} className="form-control" name="name" id="name" placeholder="Enter  Name" autoComplete='name' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Email</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-envelope fa" aria-hidden="true"></i></span>
                                        <input type="text" onChange={(e) => this.onChangeEmail(e)} className="form-control" name="email" id="email" placeholder="Enter  Email" autoComplete='email' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Password</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                        <input type="password" onChange={(e) => this.onChangePassword(e)} className="form-control" name="password" id="password" placeholder="Enter  Password" autoComplete='current-password' />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Salary</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-money fa" aria-hidden="true"></i></span>
                                        <input type="numeric" onChange={(e) => this.onChangeSalary(e)} className="form-control" name="salary" id="salary" placeholder="Enter Salary" />
                                    </div>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="cols-sm-2 control-label">Department Id</label>
                                <div className="cols-sm-10">
                                    <div className="input-group">
                                        <select name="departmentId" id="departmentId" onChange={(e) => this.onChangeDepartmentId(e)}>
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
                                        <select name="roleId" id="roleId" onChange={(e) => this.onChangeRoleId(e)}>
                                            <option value={1}>Admin</option>
                                            <option value={2}>Employee</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group ">
                                <button type="submit" className="btn btn-primary btn-lg btn-block login-button">Add User</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }

}

export default Add