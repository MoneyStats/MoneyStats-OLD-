// Graph
var ctx = document.getElementById("myChart");

var myChart = new Chart(ctx, {
  type: "line",
  data: {
    labels: [
      "Sunday",
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
    ],
    datasets: [
      {
        label: "My First dataset",
        data: [15339, 21345, 18483, 24003, 23489, 24092, 12034],
        backgroundColor: "transparent",
        borderColor: "#007bff",
        borderWidth: 4,
        backgroundColor: [
          'rgba(105, 0, 132, .2)',
          ],
          borderColor: [
          'rgba(200, 99, 132, .7)',
          ],
          borderWidth: 2
      },
      {
        label: "My Second dataset",
        data: [20000, 15000, 18000, 17000, 20000, 25207, 8790],
        backgroundColor: [
        'rgba(0, 137, 132, .2)',
        ],
        borderColor: [
        'rgba(0, 10, 130, .7)',
        ],
        borderWidth: 2
        }
    ],
  },
  
  options: {
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true,
          },
        },
      ],
    },
    legend: {
      display: true,
    },
  },
});

// Graph
var ctx1 = document.getElementById("chart-pie");

var myChart = new Chart(ctx1, {
  type: "pie",
  data: {
    labels: ["Monday", "Tuesday", "Wednesday", "Thursday"],
    datasets: [{
      data: [1234, 2234, 3234, 4234],
      backgroundColor: ["rgba(117,169,255,0.6)", "rgba(148,223,215,0.6)",
        "rgba(208,129,222,0.6)", "rgba(247,127,167,0.6)"
      ],
    }, ],
  }
});