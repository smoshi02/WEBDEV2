import ProfileCard from "./ProfileCard";

export default function ProfileList() {
  return (
    <div className="profile-container">
      <h2>Profile List</h2>

      <div className="profile-cards">
        <ProfileCard name="Ulysses Linatoc" age={55} role="Student" />
        <ProfileCard name="Zedric Rulloda" age={60} role="Student" />
        <ProfileCard name="Rhayven Alano" age={35} role="Student" />
      </div>
    </div>
  );
}
