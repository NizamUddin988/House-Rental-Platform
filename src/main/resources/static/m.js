import { useState } from "react";

function TodoApp() {
  const [todos, setTodos] = useState([]);
  const [input, setInput] = useState("");

  // Add new todo
  const addTodo = () => {
    if (input.trim() === "") return;
    setTodos([...todos, input]); // Add to list
    setInput(""); // Clear input
  };

  return (
      <div style={{ textAlign: "center", marginTop: "50px" }}>
      <h1>✅ Todo List</h1>

      {/* Input box */}
      <input
        type="text"
        value={input}
        onChange={(e) => setInput(e.target.value)}
        placeholder="Enter a task..."/>

      {/* Add button */}
      <button onClick={addTodo}>Add</button>

      {/* Display todos */}
      <ul>
        {todos.map((todo, index) => (
          <li key={index}>{todo}</li>
        ))}
      </ul>
    </div>
  );
}

export default TodoApp;
