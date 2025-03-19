// `app/page.js` is the UI for the `/` URL
import Link from "next/link";

export default function Page() {
  return (
    <div>
      <h1>Home page</h1>
      <div>
        <Link href="/task-board">
          Job search app
        </Link>
      </div>
      <div>
        <Link href="/calendar">
          Calendar
        </Link>
      </div>
    </div>
  );
}
