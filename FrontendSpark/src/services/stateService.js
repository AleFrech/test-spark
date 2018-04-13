export default (function () {
	let state;

	function initializeState () {
		if (!state) state = {};
	}

	function addFunctionToState (prop, value) {
		initializeState();
		state[prop] = value;
	}

	function getFunction (prop) {
		initializeState();
		if (!state.hasOwnProperty(prop)) console.error(`The property ${prop} does not exist in the State singleton`);
		else return state[prop];
	}

	return { addFunctionToState, getFunction };
})();
