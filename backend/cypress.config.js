const { defineConfig } = require("cypress");

module.exports = defineConfig({
  projectId: 'xtc31t',
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
