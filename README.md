# unique-id-gen
System Design Interview Question - Distributed Unique ID generator
import React from "react";
import { render } from "react-dom";
import { ResponsiveBar } from "@nivo/bar";

const styles = {
  fontFamily: "sans-serif",
  textAlign: "center"
};

const data = [
  { quarter: 1, earnings: 13000 },
  { quarter: 2, earnings: 16500 },
  { quarter: 3, earnings: 14250 },
  { quarter: 4, earnings: 19000 }
];

const App = () => (
  <div style={styles}>
    <h1>Nivo basic demo</h1>
    <div style={{ height: "400px" }}>
      <ResponsiveBar data={data} keys={["earnings"]} indexBy="quarter" />
    </div>
  </div>
);

render(<App />, document.getElementById("root"));
