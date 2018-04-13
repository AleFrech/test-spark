class authService {
    static checkSession() {
        const token = localStorage.getItem('token');
        if (!token)
            return false;
        try {
            const payload = token.split('.')[1];
            const result = JSON.parse(atob(payload));
            if (result.exp < new Date().getTime() / 1000) 
                return false;
        }
        catch (e) {
            return false;
        }
        return true;
    }
}

export default authService;