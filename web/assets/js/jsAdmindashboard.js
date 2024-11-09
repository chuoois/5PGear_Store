// Mock data for charts
document.addEventListener('DOMContentLoaded', (event) => {
    const successCount = parseInt(document.getElementById('order-success').textContent);
    const cancelledCount = parseInt(document.getElementById('order-cancelled').textContent);
    const submittedCount = parseInt(document.getElementById('order-submitted').textContent);

    const ctxOrderChart = document.getElementById('orderChart').getContext('2d');
    const orderChart = new Chart(ctxOrderChart, {
        type: 'pie',
        data: {
            labels: ['Approved', 'Cancel', 'Pending'],
            datasets: [{
                    data: [successCount, cancelledCount, submittedCount],
                    backgroundColor: ['#4caf50', '#f44336', '#ff9800'],
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                tooltip: {
                    callbacks: {
                        label: function (tooltipItem) {
                            return tooltipItem.label + ': ' + tooltipItem.raw;
                        }
                    }
                }
            }
        }
    });

    const categories = [];
    const revenues = [];
    document.querySelectorAll('.revenue-category').forEach(function (element) {
        categories.push(element.getAttribute('data-category'));
        revenues.push(parseInt(element.getAttribute('data-revenue')));
    });

    const ctxRevenueChart = document.getElementById('revenueChart').getContext('2d');
    const revenueChart = new Chart(ctxRevenueChart, {
        type: 'bar',
        data: {
            labels: categories,
            datasets: [{
                    label: 'Revenue',
                    data: revenues,
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    const ctxCustomerPieChart = document.getElementById('customerPieChart').getContext('2d');
    const customerPieChart = new Chart(ctxCustomerPieChart, {
        type: 'doughnut',
        data: {
            labels: ['Newly Registered', 'Newly Bought'],
            datasets: [{
                    data: [parseInt(document.getElementById('customers-new').textContent), parseInt(document.getElementById('customers-bought').textContent)],
                    backgroundColor: ['#36a2eb', '#ff6384'],
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
            }
        }
    });

    var ctxFeedbackChart = document.getElementById('feedbackChart').getContext('2d');

    // Tạo mảng để lưu trữ labels và dữ liệu
    var feedbackLabels = [];
    var feedbackData = [];

    document.querySelectorAll('.feedback-category').forEach(function (element) {
        feedbackLabels.push(element.getAttribute('data-category'));
        feedbackData.push(parseFloat(element.getAttribute('data-rating')));
    });

    // Tạo biểu đồ dựa trên dữ liệu thu thập được
    var feedbackChart = new Chart(ctxFeedbackChart, {
        type: 'line',
        data: {
            labels: feedbackLabels, // Labels lấy từ HTML
            datasets: [{
                    label: 'Average Star Rating',
                    data: feedbackData,
                    borderColor: 'orange',
                    fill: false
                }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});