import React, { Component } from 'react'

import apiService from '../../services/apiService';
import stateService from '../../services/stateService';


class Login extends Component {
  constructor(props) {
    super(props);

    this.state = { email: '', password: '' };
  }

  onChangeUsername(e) {
    const email = e.target.value;
    this.setState({ email });
  }

  onChangePassword(e) {
    const password = e.target.value;
    this.setState({ password });
  }

  componentDidMount(){
      localStorage.removeItem('token');
  }

  async doLogin(e) {
    e.preventDefault();

    const { email, password } = this.state;
    const body = { email, password };

    try {
      const result = await apiService.sendRequest('Login/', 'POST', body);
      const { data, message, status } = result.data;
      if (status === 'fail') {
        stateService.getFunction('showNotification')('error', 'Invalid credentials', message);
      }
      else {
        localStorage.setItem('token', data);
        this.props.history.push('/users');
      }


    } catch (err) {
      console.log(err);
    }
  }

  render() {
    return (
      <div className="container" style={{ marginTop: "30px" }}>
        <div className="col-md-4 col-md-offset-4">
          <div className="login-panel panel panel-default">
            <div className="panel-heading"><h3 className="panel-title"><strong>Sign In </strong></h3></div>
            <div className="panel-body">
              <form onSubmit={(e) => this.doLogin(e)}>
                <div className="form-group">
                  <label>Email</label>
                  <input type="email" value={this.state.email} onChange={(e) => this.onChangeUsername(e)} className="form-control" id="exampleInputEmail1" placeholder="Enter email" autoComplete="email" />
                </div>
                <div className="form-group">
                  <label>Password</label>
                  <input value={this.state.password} onChange={(e) => this.onChangePassword(e)} type="password" className="form-control" id="exampleInputPassword1" placeholder="Password" autoComplete="current-password" />
                </div>
                <button type="submit" className="btn btn-sm btn-default">Sign in</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Login
