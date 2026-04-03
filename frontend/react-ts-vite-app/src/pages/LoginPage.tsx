export default function LoginPage() {
  return (
    <form
      method="POST"
      action="/login"
      className="flex flex-col gap-4 max-w-sm mx-auto mt-32"
    >
      <h1 className="text-2xl font-bold">Sign In</h1>
      <input
        name="username"
        placeholder="Username"
        className="border p-2 rounded"
      />
      <input
        name="password"
        type="password"
        placeholder="Password"
        className="border p-2 rounded"
      />
      <button
        type="submit"
        className="bg-green-600 text-white p-2 rounded hover:bg-green-700"
      >
        Login
      </button>
    </form>
  );
}
